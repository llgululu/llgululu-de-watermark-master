module.exports = {
	// 下面请填写你的后端域名
	baseURL: "http://localhost:8004",
	tabbarChange(url) {
		uni.switchTab({
			url: url
		})
	},
	_get(url, param, success) {
		let _this = this;
		param = param || {}
		uni.getStorage({
			key: 'token',
			success: function(tokenRes) {
				uni.$u.http.get(_this.baseURL + url, {
					params: param,
					header: {
						token: tokenRes.data
					},
				}).then(res => {
					if (res.data["code"] == 2) {
						_this.wxLogin(succ => {
							let token = uni.getStorageSync('token');
							uni.$u.http.get(_this.baseURL + url, {
								params: param,
								header: {
									token: token
								},
							}).then(res => {
								success(res);
							}).catch(err => {
								console.log(err)
								uni.showToast({
									icon: "error",
									title: "网络错误"
								})
							})
						})
					} else if (res.data["code"] == 3) {
						uni.showToast({
							icon: "error",
							title: "系统维护中"
						})
					} else if (res.data["code"] == 1) {
						uni.showToast({
							icon: "error",
							title: "缺失token"
						})
					} else {
						success(res);
					}
				}).catch(err => {
					console.log(err)
					uni.showToast({
						icon: "error",
						title: "网络错误"
					})
				})
			},
			fail() {
				_this.wxLogin(suc => {
					let token = uni.getStorageSync('token');
					uni.$u.http.get(_this.baseURL + url, {
						params: param,
						header: {
							token: token
						},
					}).then(res => {
						// console.log(res.data)
						if (res.data["code"] == 2) {
							_this.wxLogin(succ => {
								let token = uni.getStorageSync('token');
								uni.$u.http.get(_this.baseURL + url, {
									params: param,
									header: {
										token: token
									},
								}).then(res => {
									success(res);
								}).catch(err => {
									console.log(err)
									uni.showToast({
										icon: "error",
										title: "网络错误"
									})
								})
							})
						} else {
							success(res);
						}
					}).catch(err => {
						console.log(err)
						uni.showToast({
							icon: "error",
							title: "网络错误"
						})
					})
				})
			}
		})
	},
	wxLogin(suc) {
		let _this = this;
		let token = uni.getStorageSync('token') || ""
		wx.login({
			provider: 'weixin',
			onlyAuthorize: true,
			success: (logincode) => {
				uni.$u.http.get(_this.baseURL + "/login/user/login", {
					params: {
						code: logincode.code
					},
					header: {
						token: token
					},
				}).then(res => {
					uni.setStorageSync("token", res.data["data"]["token"])
					suc(res)
				}).catch(err => {
					console.log(err)
					uni.showToast({
						icon: "error",
						title: "网络错误"
					})
				})
			},
			fail(err) {
				uni.showToast({
					icon: "error",
					title: "网络错误",
				})
				uni.clearStorageSync();
			}
		})
	},
	_test(url, parms, suc) {
		uni.$u.http.get(this.baseURL + url, {
			params: parms
		}).then(res => {
			suc(res)
		}).catch(err => {
			console.log(err)
			uni.showToast({
				icon: "error",
				title: "网络错误"
			})
		})
	}
}