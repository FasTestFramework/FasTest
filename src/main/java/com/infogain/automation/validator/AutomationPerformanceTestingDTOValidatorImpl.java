package com.infogain.automation.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.infogain.automation.dto.AutomationPerformanceTestingCsvConfigDto;
import com.infogain.automation.dto.AutomationPerformanceTestingHttpHandlerDto;
import com.infogain.automation.dto.AutomationPerformanceTestingTest;
import com.infogain.automation.dto.AutomationPerformanceTestingTestPlanDto;
import com.infogain.automation.dto.AutomationPerformanceTestingThreadGroupDto;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;

public class AutomationPerformanceTestingDTOValidatorImpl
                implements ConstraintValidator<AutomationPerformanceTestingDTOValidator, AutomationPerformanceTestingTest> {

	private static final int INT_VALUE_ZERO = 0; 
	
    /**
     * This method calls to validate the request payload
     * 
     * @param value the {@link AutomationBoundaryValueAnalysisDTO} to validate
     * @param context the {@link ConstraintValidatorContext}
     * @return {@code true} if requestDTO is valid<br>
     *         {@code false} if requestDTO is invalid
     * @since 15-Jul-2019
     */
    @Override
    public boolean isValid(AutomationPerformanceTestingTest value, ConstraintValidatorContext context) {
        List<ErrorCodesDTO> errorCodes;
        if (value == null) {
            throw new FastTestBadRequestException(AutomationErrorCodes.AUTOMATION_REQUESTBODY_MISSING_EXCEPTION);
        } else {
            errorCodes = new ArrayList<>();
            if (ObjectUtils.isEmpty(value.getCsvConfig())) {
                errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_PERF_TEST_MISSING_DATA_EXCEPTION, "csvConfig"));
            } else {
            	AutomationPerformanceTestingCsvConfigDto automationPerfTestingCsvConfigDto = value.getCsvConfig();
            	if(StringUtils.isBlank(automationPerfTestingCsvConfigDto.getInputCsvFilename())) {
            		errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_PERF_TEST_MISSING_DATA_EXCEPTION, "inputCsvFilename"));
            	}
            	if(StringUtils.isBlank(automationPerfTestingCsvConfigDto.getDelimiter())) {
            		automationPerfTestingCsvConfigDto.setDelimiter(",");
            	}
            	if(StringUtils.isBlank(automationPerfTestingCsvConfigDto.getIgnoreFirstline())) {
            		automationPerfTestingCsvConfigDto.setIgnoreFirstline("true");
            	}
            	if(StringUtils.isBlank(automationPerfTestingCsvConfigDto.getRecycle())) {
            		automationPerfTestingCsvConfigDto.setRecycle("false");
            	}
            	if(StringUtils.isBlank(automationPerfTestingCsvConfigDto.getShareMode())) {
            		automationPerfTestingCsvConfigDto.setShareMode("");
            	}
            	if(StringUtils.isBlank(automationPerfTestingCsvConfigDto.getVariableNames())) {
            		automationPerfTestingCsvConfigDto.setVariableNames("");
            	}
            	if(null == automationPerfTestingCsvConfigDto.getStopThread()) {
            		automationPerfTestingCsvConfigDto.setStopThread(false);
            	}
            }
            if (ObjectUtils.isEmpty(value.getThreadGroup())) {
                errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_PERF_TEST_MISSING_DATA_EXCEPTION, "threadGroup"));
            } else {
            	AutomationPerformanceTestingThreadGroupDto automationPerfTestingThreadGroupDto = value.getThreadGroup();
            	if(StringUtils.isBlank(automationPerfTestingThreadGroupDto.getThreadGroupName())) {
            		automationPerfTestingThreadGroupDto.setThreadGroupName("AutomationThreadGroup");
            	}
            	if(automationPerfTestingThreadGroupDto.getNumberOfThreads() == INT_VALUE_ZERO) {
            		automationPerfTestingThreadGroupDto.setNumberOfThreads(1);
            	}
            	if(automationPerfTestingThreadGroupDto.getRampUp() == INT_VALUE_ZERO) {
            		automationPerfTestingThreadGroupDto.setRampUp(1);
            	}
            }
            if (ObjectUtils.isEmpty(value.getHttpHandler())) {
            	errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_PERF_TEST_MISSING_DATA_EXCEPTION,
                        "httpHandler"));
            } else {
            	AutomationPerformanceTestingHttpHandlerDto automationPerfTestingHttpHandlerDto = value.getHttpHandler();
            	if(StringUtils.isBlank(automationPerfTestingHttpHandlerDto.getDomain())) {
            		errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_PERF_TEST_MISSING_DATA_EXCEPTION, "httpHandler Domain"));
            	}
            	if(ObjectUtils.isEmpty(automationPerfTestingHttpHandlerDto.getHeaders())) {
            		HashMap<String, String> headers = new HashMap<>();
            		headers.put("Content-Type", "application/json");
            		automationPerfTestingHttpHandlerDto.setHeaders(headers);
            	}
            	if(StringUtils.isBlank(automationPerfTestingHttpHandlerDto.getHttpMethod())) {
            		errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_PERF_TEST_MISSING_DATA_EXCEPTION, "httpHandler HttpMethod"));
            	}
            	if(StringUtils.isBlank(automationPerfTestingHttpHandlerDto.getName())) {
            		automationPerfTestingHttpHandlerDto.setName("AutomationPerformanceTest");
            	}
            	if(StringUtils.isBlank(automationPerfTestingHttpHandlerDto.getPath())) {
            		errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_PERF_TEST_MISSING_DATA_EXCEPTION, "httpHandler Path" ));
            	}
            	if(automationPerfTestingHttpHandlerDto.getPort() == INT_VALUE_ZERO) {
            		errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_PERF_TEST_MISSING_DATA_EXCEPTION, "httpHandler Port"));
            	}
            	if(StringUtils.isBlank(automationPerfTestingHttpHandlerDto.getProtocol())) {
            		automationPerfTestingHttpHandlerDto.setProtocol("http");
            	}
            }
            if (ObjectUtils.isEmpty(value.getTestPlan())) {
                errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_PERF_TEST_MISSING_DATA_EXCEPTION, "testPlan"));
            } else {
            	AutomationPerformanceTestingTestPlanDto automationPerfTestingTestPlanDto = value.getTestPlan();
            	if(StringUtils.isBlank(automationPerfTestingTestPlanDto.getName())) {
            		automationPerfTestingTestPlanDto.setName("AutomationTestPlan");
            	}
            }
/*            if (ObjectUtils.isEmpty(value.getAssertions())) {
                errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_PERF_TEST_MISSING_DATA_EXCEPTION, "assertions"));
            } else {
            	AutomationPerformanceTestingAssertions automationPerfTestingAssertions = value.getAssertions();
            	if(ObjectUtils.isEmpty(automationPerfTestingAssertions.getDurationAssertion())) {
            		errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_PERF_TEST_MISSING_DATA_EXCEPTION, "durationAssertion"));
            	} else {
            		AutomationPerformanceTestingDurationAssertion automationPerfTestingDurationAssertion = automationPerfTestingAssertions.getDurationAssertion();
            		if(ObjectUtils.isEmpty(automationPerfTestingDurationAssertion.getAllowedDuration())) {
            			automationPerfTestingDurationAssertion.setAllowedDuration(3000l);
            		}
            		if(ObjectUtils.isEmpty(automationPerfTestingDurationAssertion.getName())) {
            			automationPerfTestingDurationAssertion.setName("AutomationDurationAssertion");
            		}
            		if(ObjectUtils.isEmpty(automationPerfTestingDurationAssertion.getAllowedDuration())) {
            			automationPerfTestingDurationAssertion.setAllowedDuration(3000l);
            		}
            	}
            }*/
            if (!errorCodes.isEmpty()) {
                throw new FastTestBadRequestException(errorCodes);
            }
        }
        return true;
    }
}
