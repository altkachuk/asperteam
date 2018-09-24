package atproj.cyplay.com.asperteamapi.model;

import java.util.List;

/**
 * Created by andre on 22-Mar-18.
 */

public class Patient {

    String user;
    String birth_date;
    int sex;
    Integer stress_level_min;
    Integer stress_level_max;
    String emergency_contact;
    String personal_address;
    String company_address;
    Boolean rqth_recognition;
    String rqth_recognition_renew_dt;
    String mdph_notification;
    Boolean job_conversion_by_clinic;
    String job_conversion_by_clinic_description;
    String job_conversion_wanted;
    String job_conversion_approved_by_company;
    String current_job_start_dt;
    String current_company_start_dt;
    String talent_or_hobbies;
    String last_qualification;
    Boolean left_handed;
    Boolean share_best_practice;
    String subscribed_at;
    Integer activity_sector;
    List<Integer> language;

    private boolean _empty;

    public Patient() {
        _empty = true;
    }

    public String getUser() {
        return user;
    }

    public String getBirthDate() {
        return birth_date;
    }

    public int getSex() {
        return sex;
    }

    public int getStressLevelMin() {
        return stress_level_min;
    }

    public int getStressLevelMax() {
        return stress_level_max;
    }

    public String getEmergencyContact() {
        return emergency_contact;
    }

    public String getPersonalAddress() {
        return personal_address;
    }

    public String getCompanyAddress() {
        return company_address;
    }

    public Boolean isRqthRecognition() {
        return rqth_recognition;
    }

    public String getRqthRecognitionRenewDt() {
        return rqth_recognition_renew_dt;
    }

    public String getMdphNotification() {
        return mdph_notification;
    }

    public Boolean isJobConversionByClinic() {
        return job_conversion_by_clinic;
    }

    public String getJobConversionByClinicDescription() {
        return job_conversion_by_clinic_description;
    }

    public String getJobConversionWanted() {
        return job_conversion_wanted;
    }

    public String getJobConversionApprovedByCompany() {
        return job_conversion_approved_by_company;
    }

    public String getCurrentJobStartDt() {
        return current_job_start_dt;
    }

    public String getCurrentCompanyStartDt() {
        return current_company_start_dt;
    }

    public String getTalentOrHobbies() {
        return talent_or_hobbies;
    }

    public String getLastQualification() {
        return last_qualification;
    }

    public Boolean isLeftHanded() {
        return left_handed;
    }

    public Boolean isShareBestPractice() {
        return share_best_practice;
    }

    public String getSubscribedAt() {
        return subscribed_at;
    }

    public Integer getActivitySector() {
        return activity_sector;
    }

    public List<Integer> getLanguages() {
        return language;
    }

    public void setUser(String user) {
        _empty = false;
        this.user = user;
    }

    public void setBirthDate(String value) {
        _empty = false;
        this.birth_date = value;
    }

    public void setSex(int value) {
        _empty = false;
        this.sex = value;
    }

    public void setStressLevelMin(int value) {
        _empty = false;
        this.stress_level_min = value;
    }

    public void setStressLevelMax(int value) {
        _empty = false;
        this.stress_level_max = value;
    }

    public void setEmergencyContact(String value) {
        _empty = false;
        this.emergency_contact = value;
    }

    public void setPersonalAddress(String value) {
        _empty = false;
        this.personal_address = value;
    }

    public void setCompanyAddress(String value) {
        _empty = false;
        this.company_address = value;
    }

    public void setRqthRecognition(Boolean value) {
        _empty = false;
        this.rqth_recognition = value;
    }

    public void setRqthRecognitionRenewDt(String value) {
        _empty = false;
        this.rqth_recognition_renew_dt = value;
    }

    public void setMdphNotification(String value) {
        _empty = false;
        this.mdph_notification = value;
    }

    public void setJobConversionByClinic(Boolean value) {
        _empty = false;
        this.job_conversion_by_clinic = value;
    }

    public void setJobConversionByClinicDescription(String value) {
        _empty = false;
        this.job_conversion_by_clinic_description = value;
    }

    public void setJobConversionWanted(String value) {
        _empty = false;
        this.job_conversion_wanted = value;
    }

    public void setJobConversionApprovedByCompany(String value) {
        _empty = false;
        this.job_conversion_approved_by_company = value;
    }

    public void setCurrentJobStartDt(String value) {
        _empty = false;
        this.current_job_start_dt = value;
    }

    public void setCurrentCompanyStartDt(String value) {
        _empty = false;
        this.current_company_start_dt = value;
    }

    public void setTalentOrHobbies(String value) {
        _empty = false;
        this.talent_or_hobbies = value;
    }

    public void setLastQualification(String value) {
        _empty = false;
        this.last_qualification = value;
    }

    public void setLeftHanded(Boolean value) {
        _empty = false;
        this.left_handed = value;
    }

    public void setShareBestPractice(Boolean value) {
        _empty = false;
        this.share_best_practice = value;
    }

    public void setSubscribedAt(String value) {
        _empty = false;
        this.subscribed_at = value;
    }

    public void setActivitySector(Integer value) {
        _empty = false;
        this.activity_sector = value;
    }

    public void setLanguage(List<Integer> value) {
        _empty = false;
        this.language = value;
    }

    public boolean isEmpty() {
        return _empty;
    }
}
