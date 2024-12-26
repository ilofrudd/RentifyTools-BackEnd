package org.rentifytools.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tool_images")
public class ToolImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tool_id", nullable = false)
    @JsonIgnore
    private Tool tool;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Override
    public String toString() {
        return imageUrl;
    }
}
