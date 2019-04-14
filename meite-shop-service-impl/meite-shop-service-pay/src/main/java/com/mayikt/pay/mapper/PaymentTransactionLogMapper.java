package com.mayikt.pay.mapper;

import org.apache.ibatis.annotations.Insert;

import com.mayikt.pay.mapper.entity.PaymentTransactionLogEntity;

public interface PaymentTransactionLogMapper {

	@Insert("INSERT INTO `payment_transaction_log` VALUES (NULL, NULL, #{asyncLog}, NULL, #{transactionId}, null, null, NOW(), null, NOW(), null);")
	public int insertTransactionLog(PaymentTransactionLogEntity paymentTransactionLog);

}
