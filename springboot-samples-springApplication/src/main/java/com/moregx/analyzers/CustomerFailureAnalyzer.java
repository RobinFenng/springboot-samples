package com.moregx.analyzers;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

import com.moregx.exception.CustomerException;

public class CustomerFailureAnalyzer  extends AbstractFailureAnalyzer<CustomerException> {


	
	@Override
	protected FailureAnalysis analyze(Throwable rootFailure, CustomerException cause) {
		return new FailureAnalysis(
				"大兄弟，这样是错的啊",
				"改成这样才对",
				cause);
	}

}
