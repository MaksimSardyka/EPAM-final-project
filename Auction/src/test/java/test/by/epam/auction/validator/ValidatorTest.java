package test.by.epam.auction.validator;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import by.epam.auction.validator.Validator;

public class ValidatorTest {
	Validator validator;
	
	@BeforeClass
	public void init(){
		this.validator = new Validator();
	}
	
	@DataProvider
	public String[] [] unequalPasswords() {
		return new String [][]{
				{"some",""},
				{"", "some"},
				{"", "s"},
				{"", "Some"},
				{"s", "S"},
				{"some", "another"}};
	}
	
	@Test(groups = { "checkintest" }, dataProvider = "unequalPasswords")
	public void validateTwoPasswordsWontMatchEachOther(String password1, String password2){
		Assert.assertFalse(validator.passwordMatchValidate(password1, password2));
	}
	
	@DataProvider
	public String[] [] equalPasswords() {
		return new String [][]{
				{"s", "s"},
				{"some", "some"},
				{"Some", "Some"}};
	}
	
	@Test(groups = { "checkintest" }, dataProvider = "equalPasswords")
	public void validateTwoPasswordsMatchEachOther(String password1, String password2){
		Assert.assertTrue(validator.passwordMatchValidate(password1, password2));
	}
	
	@DataProvider
	public String[] [] nullPasswords() {
		return new String [][]{{null,""},
				{"s", null},
				{null, "s"},
				{null, ""},
				{null, null}};
	}
	
	@Test(groups = { "functest" }, dataProvider = "nullPasswords")
	public void shouldReturnFalseIfNullPasswordProvided(String password1, String password2){
		Assert.assertFalse(validator.passwordMatchValidate(password1, password2));
	}
}
