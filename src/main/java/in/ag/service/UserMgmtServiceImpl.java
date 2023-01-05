package in.ag.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import in.ag.binding.LoginForm;
import in.ag.binding.UnlockAccForm;
import in.ag.binding.UserForm;
import in.ag.entity.CityMaster;
import in.ag.entity.CountryMaster;
import in.ag.entity.StateMaster;
import in.ag.entity.User;
import in.ag.repository.CityRepository;
import in.ag.repository.CountryRepository;
import in.ag.repository.StateRepository;
import in.ag.repository.UserRepository;
import in.ag.utils.EmailUtils;

public class UserMgmtServiceImpl implements UserMgmtService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CountryRepository countryRepo;
	
	@Autowired
	private StateRepository stateRepo;
	
	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private EmailUtils emailUtils;
	
	
	@Override
	public String checkEmail(String email) {
		User user = userRepo.findByEmail(email);
		if(user == null) {
			return "Unique";
		}
		return "Duplicate";
	}

	@Override
	public Map<Integer, String> getCountries() {
		List<CountryMaster> countries = countryRepo.findAll();
		
		Map<Integer, String> countryMap = new HashMap<>();
		countries.forEach(country -> {
			countryMap.put(country.getCountryId(), country.getCountryName());
		});
		return countryMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		List<StateMaster> states = stateRepo.findByCountryId(countryId);
		
		Map<Integer, String> stateMap = new HashMap<>();
		states.forEach(state -> {
			stateMap.put(state.getStateId(), state.getStateName());
		});
		return stateMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		
		List<CityMaster> cities = cityRepo.findByStateId(stateId);
		
		Map<Integer, String> cityMap = new HashMap<>();
		cities.forEach(city -> {
			cityMap.put(city.getCityId(), city.getCityName());
		});
		return cityMap;
	}

	@Override
	public String registerUser(UserForm userForm) {
		// copy data from Binding obj to Entity obj
		
		User entity = new User();
		
		BeanUtils.copyProperties(userForm, entity);
		
		// Generate & Set random password
		  entity.setUserPwd(generateRandomPwd());
		
		// Set Account Status as Locked
		  entity.setAccStatus("LOCKED");
		
		userRepo.save(entity);
		
		// Send email to Unlock Account
		
		   String to = userForm.getEmail();
		   String subject = "Registration Email";
		   String body = readEmailBody("REG_EMAIL_BODY.txt", entity);
		   
		   emailUtils.sendEmail(to, subject, body);
		
		return "User Account Created";
	}

	@Override
	public String unlockAcc(UnlockAccForm unlockAccForm) {
		
		String email = unlockAccForm.getEmail();
		User user = userRepo.findByEmail(email);
		
		if(user != null && user.getUserPwd().equals(unlockAccForm.getTempPwd())) {
			user.setUserPwd(unlockAccForm.getNewPwd());
			user.setAccStatus("UNLOCKED");
			userRepo.save(user);
			return "Account Unlocked";
		}
		return "Invalid Temporary Password";
	}

	@Override
	public String login(LoginForm loginForm) {
		User user = userRepo.findByEmailAndUserPwd(loginForm.getEmail(), loginForm.getPwd());
		
		if(user == null) {
			return "Invalid Credentials";
		}
		
		if(user.getAccStatus().equals("LOCKED")) {
			return "Account Locked";
		}
		return "Success";
	}

	@Override
	public String forgotPwd(String email) {
		
		User user = userRepo.findByEmail(email);
		
		if(user == null) {
			return "No Account Found";
		}
		// send email with pwd
		
		   String subject = "Recover Password";
		   String body = readEmailBody("FORGOT_PWD_EMAIL_BODY.txt", user);
		   
		   emailUtils.sendEmail(email, subject, body);
		
		return "Password sent to your email";
	}
	
	private String generateRandomPwd() {
		String text = "SASDHDJHFHUISQWRFT6F34756937";
		
		StringBuilder sb = new StringBuilder();
		
		Random random = new Random();
		
		int pwdLength = 5;
		
		for(int i=1; i <= pwdLength; i++) {
			int index = random.nextInt(text.length());
			sb.append(text.charAt(index));
		}
		
		return sb.toString();
	}
	
	private String readEmailBody(String filename, User user) {
		
		StringBuffer sb = new StringBuffer();
		
		try(Stream<String> lines = Files.lines(Paths.get(filename))) {
			
			lines.forEach(line -> {
				line = line.replace("${FNAME}", user.getFname());
				line = line.replace("${LNAME}", user.getLname());
				line = line.replace("${TEMP_PWD}", user.getUserPwd());
				line = line.replace("${EMAIL}", user.getEmail());
				line = line.replace("${PWD}", user.getUserPwd());
				
				sb.append(line);
			});
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
