<script>
    function onBridgeReady(){
        WeixinJSBridge.invoke(
                'getBrandWCPayRequest', {
                    "appId":"${payResponse.appId}",
                    "timeStamp":"${payResponse.timeStamp}",
                    "nonceStr":"${payResponse.nonceStr}",
                    "package":"${payResponse.packAge}",
                    "signType":"${payResponse.signType}",
                    "paySign":"${payResponse.paySign}"
                },
                function(res){
                    // if(res.err_msg == "get_brand_wcpay_request:ok" ) {}
                    location.href = "${returnUrl}";
                }
        );
    }
    if (typeof WeixinJSBridge == "undefined"){
        if( document.addEventListener ){
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        }else if (document.attachEvent){
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    }else{
        onBridgeReady();
    }
</script>
