package com.shopshow.backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seller_id")
    private Long sellerId;
//    @NotNull(message = "Email cannot be null")
//    @NotBlank(message = "Email cannot be blank")
//    @Email(message = "Invalid email format")
//    private String email;
//    @NotNull(message = "Password cannot be null")
//    @NotBlank(message = "Password must not be blank")
//    @Size(min = 8, message = "Size must be atleast of 8 Character")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
//    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String contactInfo;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "company_type")
    private String companyType;
    @Column(name = "gst_number")
    private String gstNumber;
    @Column(name = "licence_number")
    private String licenceNumber;
    private String address;
    private String approvalStatus;

    public Seller() {
    }

    public Seller(Long sellerId, User user, String contactInfo, String companyName,
                  String companyType, String gstNumber, String licenceNumber, String address, String approvalStatus) {
        this.sellerId = sellerId;
        this.user = user;
        this.contactInfo = contactInfo;
        this.companyName = companyName;
        this.companyType = companyType;
        this.gstNumber = gstNumber;
        this.licenceNumber = licenceNumber;
        this.address = address;
        this.approvalStatus = approvalStatus;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}
