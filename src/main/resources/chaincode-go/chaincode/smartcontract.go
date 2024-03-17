package chaincode

import (
	"encoding/json"
	"fmt"
	"github.com/hyperledger/fabric-chaincode-go/shim"
	"github.com/hyperledger/fabric-contract-api-go/contractapi"
	"time"
)

type SmartContract struct {
	contractapi.Contract
}

type Fund struct {
	Id          string    `json:"id"`
	TradingTime time.Time `json:"tradingTime"`
	TradingType string    `json:"tradingType"`
	Payer       string    `json:"payer"`
	Receiver    string    `json:"receiver"`
	Account     float64   `json:"account"`
}

func (s *SmartContract) InitLedger(ctx contractapi.TransactionContextInterface) error {
	parse, err := time.Parse(time.RFC3339, `2024-03-04T09:00:00Z`)
	if err != nil {
		return err
	}
	funds := []Fund{
		{Id: "test1", TradingTime: parse, TradingType: "FINANCING_LOAN", Payer: "payer1", Receiver: "receiver1", Account: 1000.50},
		{Id: "test2", TradingTime: parse, TradingType: "FINANCING_REPAYMENT", Payer: "payer2", Receiver: "receiver2", Account: 2000.50},
		{Id: "test3", TradingTime: parse, TradingType: "PURCHASE_PAYMENT", Payer: "payer3", Receiver: "receiver3", Account: 3000.50},
		{Id: "test4", TradingTime: parse, TradingType: "SALE_RECEIPT", Payer: "payer4", Receiver: "receiver4", Account: 4000.50},
	}

	for _, fund := range funds {
		fundJSON, err := json.Marshal(fund)
		if err != nil {
			return err
		}

		err = ctx.GetStub().PutState(fund.Id, fundJSON)
		if err != nil {
			return fmt.Errorf("failed to put to world state. %v", err)
		}
	}
	return nil
}

func (s *SmartContract) FundExists(ctx contractapi.TransactionContextInterface, id string) (bool, error) {
	fundJSON, err := ctx.GetStub().GetState(id)

	if err != nil {
		return false, fmt.Errorf("failed to read from world state: %v", err)
	}

	return fundJSON != nil, nil
}

func (s *SmartContract) CreateFund(ctx contractapi.TransactionContextInterface, id string, tradingTime time.Time, tradingType string, payer string, receiver string, account float64) error {
	exists, err := s.FundExists(ctx, id)
	if err != nil {
		return err
	}
	if exists {
		return fmt.Errorf("the fund %s already exists", id)
	}

	fund := Fund{
		Id:          id,
		TradingTime: tradingTime,
		TradingType: tradingType,
		Payer:       payer,
		Receiver:    receiver,
		Account:     account,
	}
	fundJSON, err := json.Marshal(fund)
	if err != nil {
		return err
	}

	return ctx.GetStub().PutState(id, fundJSON)
}

func (s *SmartContract) UpdateFund(ctx contractapi.TransactionContextInterface, id string, tradingTime time.Time, tradingType string, payer string, receiver string, account float64) error {
	exists, err := s.FundExists(ctx, id)
	if err != nil {
		return err
	}
	if exists {
		return fmt.Errorf("the fund %s already exists", id)
	}

	fund := Fund{
		Id:          id,
		TradingTime: tradingTime,
		TradingType: tradingType,
		Payer:       payer,
		Receiver:    receiver,
		Account:     account,
	}
	fundJSON, err := json.Marshal(fund)
	if err != nil {
		return err
	}

	return ctx.GetStub().PutState(id, fundJSON)
}

func (s *SmartContract) DeleteFund(ctx contractapi.TransactionContextInterface, id string) error {
	exists, err := s.FundExists(ctx, id)
	if err != nil {
		return err
	}
	if !exists {
		return fmt.Errorf("the fund %s does not exist", id)
	}

	return ctx.GetStub().DelState(id)
}

func (s *SmartContract) GetAllFunds(ctx contractapi.TransactionContextInterface) ([]*Fund, error) {
	// range query with empty string for startKey and endKey does an
	// open-ended query of all funds in the chaincode namespace.
	resultsIterator, err := ctx.GetStub().GetStateByRange("", "")
	if err != nil {
		return nil, err
	}
	// 保证迭代器最终被正确关闭
	defer func(resultsIterator shim.StateQueryIteratorInterface) {
		err := resultsIterator.Close()
		if err != nil {
			fmt.Printf("Error closing results iterator: %s", err)
		}
	}(resultsIterator)

	var funds []*Fund
	for resultsIterator.HasNext() {
		next, err := resultsIterator.Next()
		if err != nil {
			return nil, err
		}

		var fund Fund

		err = json.Unmarshal(next.Value, &fund)
		if err != nil {
			return nil, err
		}
		funds = append(funds, &fund)
	}
	return funds, err
}

func (s *SmartContract) GetFundsByCompanyId(ctx contractapi.TransactionContextInterface, companyId string) ([]*Fund, error) {
	resultsIterator, err := ctx.GetStub().GetStateByRange("", "")
	if err != nil {
		return nil, err
	}
	// 保证迭代器最终被正确关闭
	defer func(resultsIterator shim.StateQueryIteratorInterface) {
		err := resultsIterator.Close()
		if err != nil {
			fmt.Printf("Error closing results iterator: %s", err)
		}
	}(resultsIterator)

	var funds []*Fund
	for resultsIterator.HasNext() {
		next, err := resultsIterator.Next()
		if err != nil {
			return nil, err
		}

		var fund Fund

		err = json.Unmarshal(next.Value, &fund)
		if err != nil {
			return nil, err
		}
		if fund.Payer == companyId || fund.Receiver == companyId {
			funds = append(funds, &fund)
		}
	}
	return funds, err
}

func (s *SmartContract) GetFundsByType(ctx contractapi.TransactionContextInterface, tradingType string) ([]*Fund, error) {
	resultsIterator, err := ctx.GetStub().GetStateByRange("", "")
	if err != nil {
		return nil, err
	}
	// 保证迭代器最终被正确关闭
	defer func(resultsIterator shim.StateQueryIteratorInterface) {
		err := resultsIterator.Close()
		if err != nil {
			fmt.Printf("Error closing results iterator: %s", err)
		}
	}(resultsIterator)

	var funds []*Fund
	for resultsIterator.HasNext() {
		next, err := resultsIterator.Next()
		if err != nil {
			return nil, err
		}

		var fund Fund

		err = json.Unmarshal(next.Value, &fund)
		if err != nil {
			return nil, err
		}
		if fund.TradingType == tradingType {
			funds = append(funds, &fund)
		}
	}
	return funds, err
}
