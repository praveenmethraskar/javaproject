package com.appprotech.campaign.common;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class Msg91OtpRequest {
    private String flow_id;
    private List<String> recipients;

    @Data
    public static class Recipient {
        private String mobiles;
        private Map<String, String> vars;
    }
}
