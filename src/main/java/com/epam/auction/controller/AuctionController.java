package com.epam.auction.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuctionController {
	@GetMapping("/me")
	public String getMyUsername(Principal principal) {
		return principal.getName();
	}

	@GetMapping("/about")
	public String getAboutSectionData() {
		return "Some text about this website";
	}

	@GetMapping("/terms")
	public String getAuctionTermsAndConditions() {
		return "Terms of use for this website";
	}
}
