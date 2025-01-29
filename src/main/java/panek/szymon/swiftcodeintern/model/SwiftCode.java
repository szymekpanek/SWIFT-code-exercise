package panek.szymon.swiftcodeintern.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "headquarters_id")
    private SwiftCode headquarters;

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

}