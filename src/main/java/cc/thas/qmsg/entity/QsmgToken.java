package cc.thas.qmsg.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "qmsg_token",
        indexes = {
                @Index(columnList = "token,status", name = "idx_token_status"),
                @Index(columnList = "expire,token", name = "idx_expire_token"),
                @Index(columnList = "audience,status", name = "idx_audience_status"),
                @Index(columnList = "issuer,token", name = "idx_issuer_token"),
                @Index(columnList = "token", name = "uk_token", unique = true)
        })
@DynamicInsert
@DynamicUpdate
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QsmgToken {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 11)
    private Long id;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @Column(name = "gmt_modified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date gmtModified;

    /**
     * token
     */
    @Column(name = "token", nullable = false, length = 255)
    private String token;

    /**
     * 过期时间
     */
    @Column(name = "expire", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expire;

    /**
     * 签发人
     */
    @Column(name = "issuer", nullable = false, length = 20)
    private String issuer;

    /**
     * 受众
     */
    @Column(name = "audience", nullable = false, length = 20)
    private String audience;

    /**
     *
     */

    /**
     * token状态
     */
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private QmsgTokenStatus status;

    @PrePersist
    void preInsert() {
        if (this.gmtCreate == null) {
            this.gmtCreate = new Date();
        }
        if (this.gmtModified == null) {
            this.gmtModified = new Date();
        }
        if (this.status == null) {
            this.status = QmsgTokenStatus.VALID;
        }
    }

    @PreUpdate
    void PreUpdate() {
        if (this.gmtModified == null) {
            this.gmtModified = new Date();
        }
    }
}
