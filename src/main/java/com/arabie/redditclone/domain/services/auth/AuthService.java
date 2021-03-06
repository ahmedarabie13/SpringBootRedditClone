package com.arabie.redditclone.domain.services.auth;

import com.arabie.redditclone.domain.dtos.AuthenticationResponseDto;
import com.arabie.redditclone.domain.dtos.UserLoginDto;
import com.arabie.redditclone.domain.dtos.UserRegisterDto;
import com.arabie.redditclone.domain.mappers.UserMapper;
import com.arabie.redditclone.domain.models.NotificationEmail;
import com.arabie.redditclone.domain.models.User;
import com.arabie.redditclone.domain.models.VerificationToken;
import com.arabie.redditclone.domain.repos.UserRepo;
import com.arabie.redditclone.domain.repos.VerificationTokenRepo;
import com.arabie.redditclone.exceptions.SpringRedditException;
import com.arabie.redditclone.proxy.MailRequestPublisher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtProviderService jwtProviderService;
    private final UserRepo userRepo;
    private final VerificationTokenRepo verificationTokenRepo;
    private final MailRequestPublisher publisher;

    //    private final MailService mailService;  commented to allow sending through rabbitmq

    @Transactional
    public void register(UserRegisterDto userRegisterDto) {
        verifyUsername(userRegisterDto.getUsername());
        var user = userMapper.registerDtoToEntity(userRegisterDto);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        var token = generateVerificationToken(user);
        var notificationEmail = NotificationEmail.builder()
                .subject("no-reply")
                .recipient(user.getEmail())
                .body("Thank you For Signing up with us, " +
                        "please click on the below url to activate your account: " +
                        "http://localhost:8080/api/auth/accountVerification/" + token).build();
        publisher.publishEmail(notificationEmail);
//        mailService.sendEmail(notificationEmail);   commented to allow sending through rabbitmq
    }

    private String generateVerificationToken(User user) {
        var token = UUID.randomUUID().toString();
        var verificationToken = VerificationToken
                .builder()
                .token(token)
                .user(user)
                .build();
        verificationTokenRepo.save(verificationToken);
        return token;
    }

    private void verifyUsername(String username) {
        if (userRepo.existsByUsername(username)) {
            throw new SpringRedditException("Username must be unique", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void verifyAccount(String token) {
        var verificationToken = verificationTokenRepo.findByToken(token);
        verificationToken.orElseThrow(() -> {
            log.error(token + " is not valid");
            return new SpringRedditException("Invalid Token", HttpStatus.FORBIDDEN);
        });
        enableVerifiedUser(verificationToken.get().getUser());
    }

    private void enableVerifiedUser(User user) {
        if (user.isEnabled()) {
            log.error("User is Already Verified");
            throw new SpringRedditException("User is Already Verified", HttpStatus.BAD_REQUEST);
        }
        user.setEnabled(Boolean.TRUE);
        userRepo.save(user);
    }

    public AuthenticationResponseDto login(UserLoginDto userLoginDto) {
        log.info(userLoginDto.toString());
        var token = userMapper.toUsernamePasswordAuthToken(userLoginDto);
        log.info(token.toString());
        var authentication = authenticationManager.authenticate(token);
        log.info(authentication.toString());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var jwt = jwtProviderService.generateToken(authentication);
        return AuthenticationResponseDto.builder()
                .authenticationToken(jwt)
                .username(userLoginDto.getUsername())
                .build();
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findByUsername(authentication.getName())
                .orElseThrow(() -> new SpringRedditException("User name not found - " + authentication.getName(), HttpStatus.NOT_FOUND));
    }

}
