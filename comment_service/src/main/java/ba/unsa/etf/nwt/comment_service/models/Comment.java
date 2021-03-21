package ba.unsa.etf.nwt.comment_service.models;

import ba.unsa.etf.nwt.comment_service.models.sectionRoles.MainRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @NotNull(message = "UserID cannot be null")
    private Long userID;

    @NotNull(message = "Title cannot be null")
    @Size(min = 2, max = 100, message
            = "Title must be between 2 and 100 characters")
    private String title;

    @NotNull(message = "Content cannot be null")
    @Size(min = 2, max = 100, message
            = "Content must be between 2 and 100 characters")
    private String content;

    @NotNull(message = "Content cannot be null")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "section_roles",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<MainRole> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<MainRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<MainRole> roles) {
        this.roles = roles;
    }
}
