package com.example.banking.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Required;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "account")
public class Account implements Comparable<Account> {


	@Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String accountId;
    private String ifscCode;
    private String name;
    private String address;
    private String city;
    private String stateOrProvince;
    private String country;
    private String postalCode;
    @NonNull
    private Integer balance;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="userId")
    private User user;
    public int compareTo(Account account) {
    		return 0;
    }
    

}