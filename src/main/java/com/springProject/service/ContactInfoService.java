package com.springProject.service;

import com.springProject.model.ContactInfo;
import com.springProject.model.User;
import com.springProject.repository.ContactInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("contactInfoService")
public class ContactInfoService {

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    public ContactInfo findById(Integer id) {

        return contactInfoRepository.findById(id);
    }

    public ContactInfo saveContactInfo(ContactInfo info) {

        return contactInfoRepository.save(info);
    }

    public void updateContactInfo(ContactInfo info) {

        contactInfoRepository.updateContactInfo(info.getCity(), info.getStreet(), info.getStreetNumber(), info.getPhone(), info.getId());
    }
}
