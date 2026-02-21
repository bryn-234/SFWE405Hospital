package SFWE405.project.code.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
    public class Patient {

        @Id
        private Long ssn;

        private String firstName;
        private String lastName;
        private LocalDate birthDate;

        public Patient() {}

        public Long getSsn() { return ssn; }
        public void setSsn(Long ssn) { this.ssn = ssn; }

        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }

        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }

        public LocalDate getBirthDate() { return birthDate; }
        public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    }
