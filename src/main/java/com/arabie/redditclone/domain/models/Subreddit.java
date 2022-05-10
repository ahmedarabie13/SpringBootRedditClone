package com.arabie.redditclone.domain.models;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@DynamicUpdate
public class Subreddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name Cannot be empty or NULL")
    private String name;
    @NotBlank(message = "Description Cannot be empty or NULL")
    private String description;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;
    @CreatedDate
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


}
