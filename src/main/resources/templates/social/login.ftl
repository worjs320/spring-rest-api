<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<a id="custom-login-btn" href="javascript:loginWithKakao()">
    <img
            src="//k.kakaocdn.net/14/dn/btqCn0WEmI3/nijroPfbpCa4at5EIsjyf0/o.jpg"
            width="222"
    />
</a>
<p id="token-result"></p>
<script type="text/javascript">

    function loginWithKakao() {
        Kakao.init('9e2262b422226b56308b488b9d0b41ac');
        Kakao.isInitialized();
        Kakao.Auth.authorize({
            redirectUri: 'http://localhost:8080/social/login/kakao'
        })
    }
    // 아래는 데모를 위한 UI 코드입니다.
    displayToken()
    function displayToken() {
        const token = getCookie('authorize-access-token')
        if(token) {
            Kakao.Auth.setAccessToken(token)
            Kakao.Auth.getStatusInfo(({ status }) => {
                if(status === 'connected') {
                    document.getElementById('token-result').innerText = 'login success. token: ' + Kakao.Auth.getAccessToken()
                } else {
                    Kakao.Auth.setAccessToken(null)
                }
            })
        }
    }
    function getCookie(name) {
        const value = "; " + document.cookie;
        const parts = value.split("; " + name + "=");
        if (parts.length === 2) return parts.pop().split(";").shift();
    }
</script>