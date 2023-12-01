var app = new Vue({
    el: '#app',
    data: {
        isLoggedIn: false,
        currentUserID: null,
        userPermission: null,
        loginCredentials: { username: "", password: "" },
        options: [],
        selectedOptions: [],
        newVoteName: "",
        loginError: false,
        loginErrorMessage: '',
        voteMessage: '',
        votedItems: [],
    },
    mounted() {
        // 檢查cookie
        const isLoggedIn = Cookies.get('isLoggedIn');
        if (isLoggedIn === "true") {
            this.isLoggedIn = true;
            this.currentUserID = Cookies.get('currentUserID');
            this.userPermission = Cookies.get('userPermission');     
        }
        // 獲得所有投票項目
        this.getallvoteitem()
    },
    methods: {
        //輸入方法驗證
        validateInput(input) {
            const regex = /^[a-zA-Z0-9]*$/;
            return regex.test(input);
        },
        //實作登入功能
        login() { 
            if (!this.validateInput(this.loginCredentials.username) || !this.validateInput(this.loginCredentials.password)) {
                this.loginError = true;
                this.loginErrorMessage = '輸入內容必須只包含英文字母和數字';
                return;
            }
            this.loginError = false;
            axios.post('http://127.0.0.1:8080/Login', this.loginCredentials)
                .then(response => {
                    if ( response.data.StatusCode === "200") {
                        this.isLoggedIn = true;
                        console.log("目前user :"+ response.data.data.voterPermission)
                        this.currentUserID = response.data.data.voterID;
                        this.userPermission = response.data.data.voterPermission;
                        // 保存到cookie
                        Cookies.set('isLoggedIn', 'true');
                        Cookies.set('currentUserID', this.currentUserID);
                        Cookies.set('userPermission', this.userPermission);
                        
                    } else {
                        this.loginError = true;
                        this.loginErrorMessage = response.data.data || 'Login failed';
                    }
                })
                .catch(error => {
                    this.loginError = true;
                    this.loginErrorMessage = 'Login error';
                    console.error('Login error:', error);
                });
        },
        //實作登出功能
        logout() {
            axios.post('http://127.0.0.1:8080/Logout', { userid: this.currentUserID })
                .then(response => {
                    if (response.data.StatusCode === "200") {
                        console.log('登出成功');
                    } else {
                        console.error('登出失敗:', response.data.data);
                    }
                })
                .catch(error => {
                    console.error('登出錯誤:', error);
                });

                // 清除cookie
                Cookies.remove('isLoggedIn');
                Cookies.remove('currentUserID');
                Cookies.remove('userPermission');

                this.isLoggedIn = false;
                this.currentUserID = null;
                this.userPermission = null;
                this.voteMessage = '';
        },
        //實作刪除功能
        deleteVoteItem(voteID) {
            axios.post('http://127.0.0.1:8080/deletevote', { voteid : voteID })
                .then(response => {
                    if (response.data.StatusCode === "200") {
                        console.log('刪除成功');
                        // 從表單中移除該項目
                        this.options = this.options.filter(item => item.voteID !== voteID);
                    } else {
                        console.error('刪除失敗:', response.data.message);
                    }
                })
                .catch(error => {
                    console.error('刪除錯誤:', error);
                });
        },
        //實作提交投票項目功能
        submitOptions() {
            const dataToSend = this.selectedOptions.map(voteID => ({
                voteid: voteID,
                voterid: this.currentUserID
            }));

            axios.post('http://127.0.0.1:8080/uservote', dataToSend)
                .then(response => {
                    this.voteMessage = '投票成功!';
                    // 投票成功後刷新
                    location.reload();
                })
                .catch(error => {
                    this.voteMessage = '投票失敗';
                    console.error('Error submitting the votes:', error);
                });
        },
        // 獲得所有投票項目
        getallvoteitem(){
            axios.get('http://127.0.0.1:8080/getvote')
            .then(response => {
                if (response.data && response.data.StatusCode === "200") {
                    this.options = response.data.data;
                } else {
                    console.error('Error fetching the options: Invalid response');
                }
            })
            .catch(error => {
                console.error('Error fetching the options:', error);
            });
        },
        addNewVoteItem() {
            // 檢查是否有填入投票項目名稱
            if (!this.validateInput(this.newVoteName)) {
                alert("投票項目名稱必須只包含英文字母和數字");
                return;
            }
            if (!this.newVoteName) {
                alert("請輸入欲建立的新投票項目名稱");
                return;
            }
    
            // 建立傳遞到後端的json
            const newVoteItem = {
                votename: this.newVoteName,
            };
    
            //傳送到後端
            axios.post('http://127.0.0.1:8080/setvote', newVoteItem)
                .then(response => {
                    if (response.data.StatusCode === "200") {
                        // 添加成功後，加入倒options當中
                        this.options.push(response.data.data);
                        // 清空輸入框
                        this.newVoteName = ""; 
                        console.log('添加成功');
                        // 投票成功後刷新
                        location.reload();

                    } else {
                        console.error('添加失敗:', response.data.message);
                    }
                })
                .catch(error => {
                    console.error('添加錯誤:', error);
                });
        },
    }
});