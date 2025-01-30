package panek.szymon.swiftcodeintern.model;

import jakarta.persistence.*;



@Entity
public class SwiftCode {
    @Id
    @Column(nullable = false, unique = true)
    private String swiftCode;

    private String bankName;
    private String countryName;
    private String countryISO2;
    private boolean isHeadquarter;
    private String address;


    public SwiftCode(String swiftCode, String bankName, String countryName, String countryISO2, boolean isHeadquarter, String address) {
        this.swiftCode = swiftCode;
        this.bankName = bankName;
        this.countryName = countryName;
        this.countryISO2 = countryISO2;
        this.isHeadquarter = isHeadquarter;
        this.address = address;
    }

    public SwiftCode() {

    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryISO2() {
        return countryISO2;
    }

    public void setCountryISO2(String countryISO2) {
        this.countryISO2 = countryISO2;
    }

    public boolean isHeadquarter() {
        return isHeadquarter;
    }

    public void setHeadquarter(boolean headquarter) {
        isHeadquarter = headquarter;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
