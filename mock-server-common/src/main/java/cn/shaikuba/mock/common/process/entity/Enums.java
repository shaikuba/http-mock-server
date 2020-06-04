package cn.shaikuba.mock.common.process.entity;

public interface Enums {

    enum Partner {
        EBU("ebu"), BAOFU("baofu"), JD("jd"), ICBC("icbc"), ICBCSZ("icbcsz"), ABC("abc"), YINYIN("yinyin");

        private String partner;
        Partner(String partner) {
            this.partner = partner;
        }

        public String getPartner() {
            return partner;
        }
    }

    enum Service {
        PREAUTH("preAuth"), AUTHSIGN("authSign");

        private String serviceName;

        Service(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getServiceName() {
            return serviceName;
        }

    }

}
