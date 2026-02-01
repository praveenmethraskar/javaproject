package com.appprotech.campaign.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OriginalDataRequest {


            private String  voter_id;
            private long sl_no;
            private String name;
            private String house_number;
            private long age;
            private String gender;
            private String relation_name;
            private String relation_type;


}
