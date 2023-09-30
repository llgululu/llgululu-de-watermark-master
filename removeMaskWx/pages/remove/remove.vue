<template>
	<view class="content">
		<view class="remove">
			<view class="remove-title-clearButton">
				<view class="remove-title">
					<view class="remove-title-content">
						<u--text bold size="18" color="#0081ff" text="快速去水印"></u--text>
					</view>
					<view class="remove-title-shadow">
						<u--text bold color="#0081ff" text="SPEEDY"></u--text>
					</view>
				</view>
				<view class="remove-clearButton">
					<u--text @click="clearTextarea" text="清空"></u--text>
				</view>
			</view>
			<view class="remove-textarea">
				<u--textarea maxlength="180" border="none" height="140" v-model="input_url"
					placeholder="请粘贴需要提取的视频/图集链接"></u--textarea>
			</view>
			<view class="remove-paste-submit-button">
				<view class="remove-paste-button">
					<u-button @click="pasteURL" type="primary" plain text="粘贴链接"></u-button>
				</view>
				<view class="remove-submit-button">
					<u-button @click="getAnalysisDate" type="primary" text="立即解析"></u-button>
				</view>
			</view>
			<view class="remove-question">
				<view class="remove-question-black">
					<u--text text="有问题？请看"></u--text>
				</view>
				<view class="remove-question-blue">
					<u--text decoration="underline" @click="jumpPage('/pages/remove/problem')" type="primary"
						text="教程和问题"></u--text>
				</view>

			</view>
		</view>
		<view class="ad-show" v-if="isOpenAd">
			<ad ad-intervals="30" :unit-id="videoAd_id" ad-type="video" ad-theme="white"></ad>
		</view>
		<view class="u-tabbar">
			<u-tabbar :value="tabbarIndex" :fixed="true" :placeholder="true" :safeAreaInsetBottom="true">
				<u-tabbar-item v-for="(item,index) in tabbarList" :key="index" :text="item.text" @click="tabbarClick"
					:icon="item.icon"></u-tabbar-item>
			</u-tabbar>
		</view>
	</view>
</template>

<script>
	var App = require("@/common.js");
	let interstitialAd = null;
	export default {
		data() {
			return {
				isOpenAd: false,
				videoAd_id: "",
				interstitialAd_id: "",
				input_url: "",
				tabbarIndex: 1,
				tabbarList: [{
						text: "首页",
						icon: "home",
						pagePath: "/pages/index/index"
					},
					{
						text: "去水印",
						icon: "https://www.myazurepicture.llgulugulu.top/api/picture/getImageByte?pid=13c461e315f437d9b192f253e24599c6",
						pagePath: "/pages/remove/remove"
					},
					{
						text: "我的",
						icon: "account",
						pagePath: "/pages/mine/mine"
					}
				]
			}
		},
		onLoad() {
			let _this = this;
			uni.getStorage({
				key: 'show_setting',
				success: function(res) {
					// console.log(res.data);
					_this.isOpenAd = res.data["seIsOpenAd"]
					_this.videoAd_id = res.data["seAdVideoId"]
					_this.interstitialAd_id = res.data["seAdInterstitialId"]
					if (_this.isOpenAd) {
						if (wx.createInterstitialAd) {
							interstitialAd = wx.createInterstitialAd({
								adUnitId: _this.interstitialAd_id
							})
							interstitialAd.onLoad(() => {
								console.log('onLoad event emit')
							})
							interstitialAd.onError((err) => {
								console.log('onError event emit', err)
							})
							interstitialAd.onClose((res) => {
								console.log('onClose event emit', res)
							})
						}
					}
				}
			});

		},
		onShow() {
			let _this = this;
			wx.hideTabBar()
			if (_this.isOpenAd && interstitialAd) {
				interstitialAd.show().catch((err) => {
					console.error(err)
				})
			}
		},
		methods: {
			getStrUrl(s) {
				var reg = /(http:\/\/|https:\/\/)((\w|=|\?|\.|\/|&|-)+)/g;
				var reg = /(https?|http|ftp|file):\/\/[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]/g;
				s = s.match(reg);
				return (s && s.length ? s[0] : null);
			},
			getAnalysisDate() {
				let _this = this;
				if (_this.input_url == "" || _this.input_url == null) {
					uni.showToast({
						title: '请填写内容后解析哦！',
						icon: 'none',
						duration: 2000
					});
				} else {
					let getUrl = _this.getStrUrl(_this.input_url)
					if (getUrl == null) {
						uni.showToast({
							title: '糟糕！您填写的内容找不到解析地址，无法解析！',
							icon: 'none',
							duration: 2000
						});
						_this.input_url = ''
						return;
					}
					uni.showLoading({
						title: '解析中...',
						mask: true
					});
					App._get("/api/remove/getAnalysisDate", {
						url: getUrl
					}, res => {
						uni.hideLoading()
						if (res.data["code"] != 200) {
							uni.showToast({
								title: res.data["msg"],
								icon: 'none',
								duration: 2000
							});
						} else {
							uni.setStorageSync("analysisData", res.data["data"])
							_this.input_url = ''
							if (res.data["data"]["type"] == "video") {
								_this.jumpPage("/pages/remove/video")
							} else {
								_this.jumpPage("/pages/remove/image")
							}
						}
					})
				}

			},
			tabbarClick(e) {
				let _this = this;
				App.tabbarChange(_this.tabbarList[e].pagePath)
			},
			pasteURL() {
				let _this = this;
				uni.getClipboardData({
					success: function(res) {
						// console.log(res.data)
						_this.input_url = res.data
					},
					fail(e) {
						console.log(e)
					}
				})
			},
			clearTextarea() {
				let _this = this;
				uni.showModal({
					title: '提示',
					content: '确定要清空当前输入框吗？',
					success: res => {
						if (res.confirm) {
							_this.input_url = ""
						}
					}
				})
			},
			jumpPage(url) {
				uni.navigateTo({
					url: url
				})
			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: $u-bg-color;
	}

	.remove {
		padding: 20rpx 20rpx 20rpx 20rpx;
	}

	.remove-title-clearButton {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}

	.remove-textarea {
		margin-top: 30rpx;
		border-radius: 25rpx;
		background-color: #f8f8f8;
		box-shadow: 0px 5px 5px 0px #EDEDED;
	}

	.remove-clearButton {
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;

	}

	.remove-title {
		position: relative;
	}

	.remove-title-shadow {
		position: absolute;
		bottom: -20rpx;
		font-style: italic;
		opacity: 0.4;
		padding-left: 10rpx;
	}

	.u-textarea[data-v-09988a29] {
		border-radius: 12px;
		background-color: #fff;
		position: relative;
		display: flex;
		flex-direction: row;
		flex: 1;
		padding: 9px;
	}

	.remove-paste-submit-button {
		padding-top: 35rpx;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}

	.remove-paste-button,
	.remove-submit-button {
		width: 45%;
		box-shadow: 3px 3px 4px rgba(0, 102, 204, 0.2);
	}

	.remove-question {
		width: 100%;
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		padding-top: 10rpx;
	}

	.remove-question-black {
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		padding-right: 10rpx;
	}

	.remove-question-blue {
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
	}

	.ad-show {
		width: 100%;
		padding-top: 30rpx;
	}
</style>