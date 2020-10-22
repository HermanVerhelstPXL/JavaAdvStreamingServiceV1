package be.pxl.ja.streamingservice.model;

import be.pxl.ja.streamingservice.exception.TooManyProfilesException;
import be.pxl.ja.streamingservice.util.PasswordUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Account {
	public static final int MINIMUM_PASSWORD_STRENGTH = 5;
	private String email;
	private String password;
	private PaymentInfo paymentInfo;
	private StreamingPlan streamingPlan;
	private List<Profile> profiles = new ArrayList<>();


	public Account(String email, String password) {
		if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
			throw new IllegalArgumentException("email or password is null or empty");
		}
		this.email = email;
		setPassword(password);
		setStreamingPlan(StreamingPlan.BASIC);
		profiles.add(new Profile("Profile1"));
	}

	public void setStreamingPlan(StreamingPlan streamingPlan) {
		this.streamingPlan = streamingPlan;
	}

	public void addProfile(Profile profile) throws TooManyProfilesException {
		if (profiles.size() >= streamingPlan.getNumberOfScreens()) {
			throw new TooManyProfilesException("number of profiles exceeds maximum number of screens allowed in your plan");
		}
		profiles.add(profile);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean verifyPassword(String password) {
		return PasswordUtil.isValid(password, this.password);
	}

	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public void setPassword(String password) {
		if (PasswordUtil.calculateStrength(password) < MINIMUM_PASSWORD_STRENGTH) {
			throw new IllegalArgumentException("the strength of your password is below 5");
		}
		this.password = PasswordUtil.encodePassword(password);
	}

	public Profile getFirstProfile() {
		return profiles.get(0);
	}

	public List<Profile> getProfiles() {
		// profiles.sort(Comparator.comparing(Profile::getName));
		Collections.sort(profiles);
		return profiles;
	}

	public int getNumberOfProfiles() {
		return profiles.size();
	}
}
