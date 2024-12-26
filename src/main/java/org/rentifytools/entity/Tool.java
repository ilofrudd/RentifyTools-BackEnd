package org.rentifytools.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.rentifytools.enums.ToolsAvailabilityStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "user")
@Entity
@Table(name = "tools")
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @JsonIgnore
    private User user;

    @Column(name = "title")
    @Size(min = 3, max = 50, message = "Advert title can be between 3 and 63 characters long")
    private String title;

    @Column(name = "description")
    @Size(max = 1500, message = "Description may contain no more than 1500 characters.")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private ToolsAvailabilityStatus status;

    @Column(name = "price")
    @DecimalMin(value = "0.0", message = "Price must be a positive number and can't be less than 0.")
    private Double price;

    @OneToMany(mappedBy = "tool", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ToolImage> imageUrls = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tool_categories",
            joinColumns = @JoinColumn(name = "tool_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonManagedReference
    private List<Category> categories = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tool tool = (Tool) o;
        return Objects.equals(id, tool.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}