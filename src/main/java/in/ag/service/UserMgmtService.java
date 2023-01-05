package in.ag.service;

import java.util.Map;

import in.ag.binding.LoginForm;
import in.ag.binding.UnlockAccForm;
import in.ag.binding.UserForm;

public interface UserMgmtService {
	
	public String checkEmail(String email);
	
	public Map<Integer, String> getCountries();
	
	public Map<Integer, String> getStates(Integer countryId);
	
	public Map<Integer, String> getCities(Integer stateId);
	
	public String registerUser(UserForm user);
	
	public String unlockAcc(UnlockAccForm accForm);
	
	public String login(LoginForm loginForm);
	
	public String forgotPwd(String email);

}
