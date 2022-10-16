package com.example.springSecurity.person.right;

import javax.persistence.*;
@Entity(name = "Right")
@Table(name ="Rights",
        uniqueConstraints = {
                @UniqueConstraint(name = "right_unique",
                        columnNames = "rightName")
        })
public class RightEntity {
    @Id
    @SequenceGenerator(
            name = "right_sequence",
            sequenceName = "right_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "right_sequence"
    )
    @Column(name = "RightId")
    private Integer rightId;

    @Column (name = "RightName")
    private String rightName;

    public RightEntity(Integer rightId, String rightName) {
        this.rightId = rightId;
        this.rightName = rightName;
    }

    public RightEntity(String rightName) {
        this.rightName = rightName;
    }

    public RightEntity() {
    }

    public Integer getRightId() {
        return rightId;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    @Override
    public String toString() {
        return "RightEntity{" +
                "rightId=" + rightId +
                ", rightName='" + rightName + '\'' +
                '}';
    }
}
