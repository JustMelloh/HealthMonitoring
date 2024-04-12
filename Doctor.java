    /**
     * Represents a doctor in the system.
     */
    public class Doctor extends User {
        private String medicalLicenseNumber;
        private String specialization;

        /**
         * Constructs a new Doctor object with the specified details.
         *
         * @param id the ID of the doctor
         * @param firstName the first name of the doctor
         * @param lastName the last name of the doctor
         * @param email the email of the doctor
         * @param password the password of the doctor
         * @param isDoctor a boolean indicating if the user is a doctor
         * @param medicalLicenseNumber the medical license number of the doctor
         * @param specialization the specialization of the doctor
         */
        public Doctor(int id, String firstName, String lastName, String email, String password, boolean isDoctor, String medicalLicenseNumber, String specialization) {
            super(firstName, lastName, email, password, isDoctor);
            this.medicalLicenseNumber = medicalLicenseNumber;
            this.specialization = specialization;
        }

        /**
         * Gets the medical license number of the doctor.
         *
         * @return the medical license number
         */
        public String getMedicalLicenseNumber() {
            return medicalLicenseNumber;
        }

        /**
         * Sets the medical license number of the doctor.
         *
         * @param medicalLicenseNumber the medical license number to set
         */
        public void setMedicalLicenseNumber(String medicalLicenseNumber) {
            this.medicalLicenseNumber = medicalLicenseNumber;
        }

        /**
         * Gets the specialization of the doctor.
         *
         * @return the specialization
         */
        public String getSpecialization() {
            return specialization;
        }

        /**
         * Sets the specialization of the doctor.
         *
         * @param specialization the specialization to set
         */
        public void setSpecialization(String specialization) {
            this.specialization = specialization;
        }
    }


