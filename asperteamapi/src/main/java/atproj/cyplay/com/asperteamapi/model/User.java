package atproj.cyplay.com.asperteamapi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by andre on 22-Mar-18.
 */

public class User {

    String id;
    Patient patient;
    List<String> patient_job;
    List<UserData> patients;
    List<UserData> staff;
    String username;
    String first_name;
    String last_name;
    String email;
    String firebase_token;
    String role;
    String image;
    String phone;
    String function;
    String company;
    String office_number;
    String experience;
    String availability_as_coach;
    String fb_account;
    String coach_origin;
    String permanent_coach_origin;

    private boolean _empty;

    public User() {
        _empty = true;
    }

    public boolean isEmpty() {
        return _empty;
    }

    public String getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public List<Integer> getJobIds() {
        List<Integer> ids = new ArrayList<>();
        if (patient_job == null)
            return ids;

        for (int i = 0; i < patient_job.size(); i++) {
            String url = patient_job.get(i);
            String[] s = url.split("/");
            String sId = s[s.length - 1];
            int id = Integer.parseInt(sId);
            ids.add(id);
        }
        return ids;
    }

    public String getStaffId(RoleType roleType) {
        if (roleType == RoleType.COACH)
            return coach_origin;
        if (roleType == RoleType.COACH_PERMANENT)
            return permanent_coach_origin;

        for (int i = 0; i < staff.size(); i++) {
            if (staff.get(i).getRoleType() == roleType) {
                String url = staff.get(i).getUrl();
                String[] s = url.split("/");
                return s[s.length - 1];
            }
        }
        return null;
    }

    public List<String> getPatientIds() {
        List<String> patientIds = new ArrayList<>();
        if (patients != null) {
            for (int i = 0; i < patients.size(); i++) {
                String url = patients.get(i).getUrl();
                String[] p = url.split("/");
                patientIds.add(p[p.length - 1]);
            }
        }
        return patientIds;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getFirebaseToken() {
        return firebase_token;
    }

    public RoleType getRole() {
        return RoleType.values()[Integer.parseInt(role)];
    }

    public String getImage() {
        return image;
    }

    public String getPhone() {
        return phone;
    }

    public String getFunction() {
        return function;
    }

    public String getCompany() {
        return company;
    }

    public String getOfficeNumber() {
        return office_number;
    }

    public String getExperience() {
        return experience;
    }

    public String getAvailabilityAsCoach() {
        return availability_as_coach;
    }

    public String getFbAccount() {
        return fb_account;
    }

    public String getFbId() {
        if (fb_account == null)
            return null;
        String url = fb_account;
        String[] s = url.split("/");
        return s[s.length - 1];
    }

    public String getCoachOrigin() {
        return coach_origin;
    }

    public String getPermanentCoachOrigin() {
        return permanent_coach_origin;
    }

    public void setPatient(Patient value) {
        _empty = false;
        patient = value;
    }

    public void setPatientJob(List<String> value) {
        _empty = false;
        patient_job = value;
    }

    public void setStaff(HashMap<String, Integer> staff) {
        this.staff = new ArrayList<>();
        Iterator it = staff.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            int value = staff.get(key);
            this.staff.add(new UserData(key, value));
        }
    }

    public void setUsername(String value) {
        _empty = false;
        username = value;
    }

    public void setFirstName(String value) {
        _empty = false;
        first_name = value;
    }

    public void setLastName(String value) {
        _empty = false;
        last_name = value;
    }

    public void setEmail(String value) {
        _empty = false;
        email = value;
    }

    public void setRole(String value) {
        _empty = false;
        role = value;
    }

    public void setImage(String value) {
        _empty = false;
        image = value;
    }

    public void setPhone(String value) {
        _empty = false;
        phone = value;
    }

    public void setFunction(String value) {
        _empty = false;
        function = value;
    }

    public void setCompany(String value) {
        _empty = false;
        company = value;
    }

    public void setOfficeNumber(String value) {
        _empty = false;
        office_number = value;
    }

    public void setExperience(String value) {
        _empty = false;
        experience = value;
    }

    public void setAvailabilityAsCoach(String value) {
        _empty = false;
        availability_as_coach = value;
    }

    public void setFbAccount(String value) {
        _empty = false;
        fb_account = value;
    }

    public void setCoachOrigin(String value) {
        _empty = false;
        coach_origin = value;
    }

    public void setPermanentCoachOrigin(String value) {
        _empty = false;
        permanent_coach_origin = value;
    }


    class UserData {
        String url;
        int role;

        public UserData(String url, int role) {
            this.url = url;
            this.role = role;
        }

        public RoleType getRoleType() {
            return RoleType.values()[role];
        }

        public String getUrl() {
            return url;
        }
    }
}
