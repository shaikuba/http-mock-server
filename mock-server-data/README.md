## DDAP Service Request And Response Body Description

#### DDAP-EBU
- PreAuth API
```javadoc
    //Request Body:
    Map<String, String> requestMap = new HashMap<>();
    requestMap.put("requestsn", request.getRequestSn());
    requestMap.put("requesttime", request.getSendTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    requestMap.put("mccode", ebuConfiguration.getMccode());
    requestMap.put("cardno", preAuth.getCustomerBankAccount());
    requestMap.put("id", preAuth.getCustomerIdCardNumber());
    requestMap.put("idtype", EBUConfiguration.ID_TYPE);
    requestMap.put("username", preAuth.getCustomerName().trim());
    requestMap.put("mobile", preAuth.getCustomerPhoneNumber());
    requestMap.put("smstype", EBUConfiguration.SMS_TYPE);
    //EBU所需要的签名字段
    requestMap.put("sign", ebuConfiguration.generateSign(ebuConfiguration.getMD5(requestMap)));
    
    //Response Body:
    this.providerRetCode = responseMap.get("retcode").toString();
    this.providerRequestSn = responseMap.get("requestsn").toString();
    this.sign = responseMap.get("sign").toString();
```
- AuthSign API
```javadoc
     Request Body
     Map<String, String> requestMap = new HashMap<>();
     requestMap.put("requestsn", authSign.getRequestSn());
     requestMap.put("mccode", ebuConfiguration.getMccode());
     requestMap.put("requesttime", authSign.getSendTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
     requestMap.put("cardno", authSign.getAuthSign().getCustomerBankAccount());
     requestMap.put("id", authSign.getAuthSign().getCustomerIdCardNumber());
     requestMap.put("idtype", EBUConfiguration.ID_TYPE);
     requestMap.put("username", authSign.getAuthSign().getCustomerName().trim());
     requestMap.put("mobile", authSign.getAuthSign().getCustomerPhoneNumber());
     requestMap.put("verifycode", authSign.getAuthSign().getSmsCode());
     requestMap.put("sms_requestsn", authSign.getAuthSign().getUniqueCode());
     String sign = ebuConfiguration.generateSign(ebuConfiguration.getMD5(requestMap));
     requestMap.put("sign", sign);
     
     Response Body
     this.provideResCode = Optional.ofNullable(responseMap.get("retcode")).orElse("").toString();
     this.contractNo = Optional.ofNullable(responseMap.get("contractno")).orElse("").toString();
     this.sign = Optional.ofNullable(responseMap.get("sign")).orElse("").toString();
```

#### DDAP-YINYIN
- PreAuth API
```java snippet
    //Request Body:
    paramMap.put("version", yinYinConfiguration.getVersion());
    paramMap.put("mchtId", yinYinConfiguration.getMerchantId());
    paramMap.put("signType", yinYinConfiguration.getSignType());
    paramMap.put("serialNo", requestSn);
    paramMap.put("transTime", getEndDateTime());
    paramMap.put("acctNo", Signature.encryptByCFCA(preAuth.getCustomerBankAccount(), getPubServerCert()));
    paramMap.put("acctType", yinYinConfiguration.getAcctType());
    paramMap.put("acctName", Signature.encryptByCFCA(preAuth.getCustomerName(), getPubServerCert()));
    paramMap.put("certType", yinYinConfiguration.getCertType());
    paramMap.put("certNo", Signature.encryptByCFCA(preAuth.getCustomerIdCardNumber(), getPubServerCert()));
    paramMap.put("rsrvPhoneNo", Signature.encryptByCFCA(preAuth.getCustomerPhoneNumber(), getPubServerCert()));
    paramMap.put("pubOrPriFlag", yinYinConfiguration.getPubOrPriFlag());
    
    String signaturedMac = Signature.generateMAC(paramMap, getPriMerchantCert(),
            getPriMerchantCertPwd());
    paramMap.put("mac", signaturedMac);

    // Response Body:
    {
        "mac": "signature string",
        "signType": "RSA",
        "data": [],
        "childInstList": [],
        "respCode": "E0000",  // Or "errcode"
        
    }
```
Request Body:
```json
    {
        "version" : "<version>",
        "mchtId" : "ORG070000000000011111",
        "mac" : "C380BEC2BFD727A4B6845133519F3AD6",
        "signType" : "RSA",
        "serialNo" : "1009666387",
        "transTime" : "20160606170856",
        "acctNo" : "350101199101011234",
        "acctType" : 1,
        "acctName" : "胡斐",
        "certType" : 0,
        "certNo" : "340123198911092013",
        "rsrvPhoneNo" : "13812345678",
        "expireDate" : "1020",
        "cvn" : "123"
    }
```
Response Body:
```json
    {
        "version" : "<version>",
        "mchtId" : "ORG070000000000011111",
        "mac" : "C380BEC2BFD727A4B6845133519F3AD6",
        "serialNo" : "1009666387",
        "respCode" : "E0000",
        "respMsg" : "处理成功"
    }

```
```json
    {
        "version" : "<version>",
        "mchtId" : "ORG070000000000011111",
        "mac" : "C380BEC2BFD727A4B6845133519F3AD6",
        "serialNo" : "1009666387",
        "respCode" : "E0522",
        "respMsg" : "商户信息不存在"
    }
```
- AuthSign API
Request
```json
    {
        "version" : "<version>",
        "mchtId" : "ORG070000000000011111",
        "mac" : "C380BEC2BFD727A4B6845133519F3AD6",
        "signType" : "RSA",
        "serialNo" : "1009666387123",
        "phoneNo" : "13812345678",
        "smsCode" : "09527"
    }
```
Response Success
```json
    {
        "version" : "<version>",
        "mchtId" : "ORG070000000000011111",
        "mac" : "C380BEC2BFD727A4B6845133519F3AD6",
        "serialNo" : "1009666387123",
        "respCode" : "E0000",
        "respMsg" : "操作成功"
    }
```

Response Success
```json
    {
        "version" : "<version>",
        "mchtId" : "ORG070000000000011111",
        "mac" : "C380BEC2BFD727A4B6845133519F3AD6",
        "serialNo" : "1009666387",
        "respCode" : "E0524",
        "respMsg" : "生成短信验证失败"
    }
```


