package miu.waa.group5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String role;

    private boolean enabled;

    private String name;

    private String imageUrl;

    @Column(columnDefinition = "boolean default true")
    private boolean isActive;

    @Column(columnDefinition = "boolean default true")
    private boolean isApproved;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

//    @PrePersist
//    @PreUpdate
//    private void hashPassword() {
//        if (password != null) {
//            this.password = new BCryptPasswordEncoder().encode(password);
//        }
//    }

}
