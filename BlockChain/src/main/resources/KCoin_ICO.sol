//kcoin ICO

pragma solidity ^0.4.0;

contract KCoin_ICO{
    
    //Maximium Kcoins avaialble to sales
    uint public max_kcoins = 1000000;
    
    //USD to Kcoin conversion private
    uint public usd_to_kcoins =100;
    
    //total number of Kcoins bought by investors in in ICO
    uint public total_kcoins_bought = 0;
    
    //mappings to investors address to equity and usd
    mapping(address => uint) equity_kcoin;
    mapping(address => uint) equity_usd;
    
    modifier kcoin_can_buy(uint usd_invested){
        
        require(usd_invested  * usd_to_kcoins + total_kcoins_bought <= max_kcoins);
        _;
    }
    
    //Getting equity to Kcoins
    function equity_in_kcoin(address investor) external constant returns(uint){
        return equity_kcoin[investor];
    }
    
    //Getting equity to usd_to_kcoins
    function equity_in_usd(address investor) external constant returns(uint){
        
        return equity_usd[investor];
    }
    
    //Buy Kcoins
    function buy_kcoin(address investor, uint usd_invested) external 
    kcoin_can_buy(usd_invested){
       uint kcoin_bought = usd_invested * usd_to_kcoins;
        equity_kcoin[investor] += kcoin_bought;
        equity_usd[investor] = equity_kcoin[investor] /usd_to_kcoins;
        total_kcoins_bought += kcoin_bought;
        
    }
    
     //Sell Kcoins
    function sell_kcoin(address investor, uint kcoins_to_sell) external {
        equity_kcoin[investor] -= kcoins_to_sell;
        equity_usd[investor] = equity_kcoin[investor] /usd_to_kcoins;
        total_kcoins_bought -= kcoins_to_sell;
        
    }
} 