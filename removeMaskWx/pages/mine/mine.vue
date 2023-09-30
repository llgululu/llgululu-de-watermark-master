<template>
	<view class="content">
		<view class="account-head-name-id">
			<view class="account-head-pic">
				<u--image :src="imageurl" shape="circle" width="140rpx" height="140rpx"></u--image>
			</view>
			<view class="account-name">
				<u--text color="white" size="17" :text="'用户'+userId"></u--text>
			</view>
			<view class="account-id">
				<u--text color="white" size="14" :text="'id: '+userId"></u--text>
			</view>
		</view>
		<view class="account-use-total-count">
			<view class="account-count">
				<view class="account-total-count">
					<u-icon name="hourglass-half-fill" space="6" labelSize="16"
						:label="'现剩余:'+totalSurplusCount+'次'"></u-icon>
				</view>
			</view>
			<view class="account-count">
				<view class="account-use-count">
					<u-icon name="hourglass" labelSize="16" space="6" :label="'已使用:'+totalUseCount+'次'"></u-icon>
				</view>
			</view>
		</view>
		<view class="addUserCount">
			<view @click="watchVideoAdd" class="addUserCount-cell">
				<view class="addUserCount-cell-text">
					<u-icon name="man-add" space="6" labelSize="16" label="观看视频增加次数(+4)"></u-icon>
				</view>
				<view class="cell-rightIcon">
					<u-icon name="arrow-right"></u-icon>
				</view>
			</view>
		</view>
		<view class="addUserCount">
			<view class="addUserCount-cell">
				<view class="addUserCount-cell-text">
					<u-icon name="kefu-ermai" space="6" labelSize="16" label="需更多次数请联系客服"></u-icon>
				</view>
				<view class="cell-rightIcon">
					<u-icon name="arrow-right"></u-icon>
				</view>
				<view class="kefu">
					<button open-type="contact" class="kufu-button"></button>
				</view>
			</view>
		</view>
		<view v-if="isOpenAd" class="bannerAd">
			<ad ad-intervals="30" :unit-id="bannerAd_id"></ad>
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
	let rewardedVideoAd = null;
	export default {
		data() {
			return {
				isOpenAd: false,
				interstitialAd_id: "",
				rewardedVideoAd_id: "",
				bannerAd_id: "",
				imageurl: 'https://www.myazurepicture.llgulugulu.top/api/picture/getImageByte?pid=7d913b1a31f6cf66d71bebde97d1bd30',
				tabbarIndex: 2,
				tabbarList: [{
						text: "首页",
						icon: "home",
						pagePath: "/pages/index/index"
					},
					{
						text: "去水印",
						icon: "https://www.myazurepicture.llgulugulu.top/api/picture/getImageByte?pid=22f0253589a9159603bc767c137d9545",
						pagePath: "/pages/remove/remove"
					},
					{
						text: "我的",
						icon: "account",
						pagePath: "/pages/mine/mine"
					}
				],
				userId: 1,
				totalUseCount: 0,
				totalSurplusCount: 0,
			}
		},
		onShow() {
			let _this = this;
			wx.hideTabBar();
			if (_this.isOpenAd && interstitialAd) {
				interstitialAd.show().catch((err) => {
					console.error(err)
				})
			}
			App._get("/api/mine/getUserInfo", {}, res => {
				_this.userId = res.data["data"]["userinfo"]["uId"];
				_this.totalUseCount = res.data["data"]["userinfo"]["uTotalUse"];
				_this.totalSurplusCount = res.data["data"]["userinfo"]["uSysCount"]
			})
		},
		onLoad() {
			let _this = this;
			uni.getStorage({
				key: 'show_setting',
				success: function(res) {
					_this.isOpenAd = res.data["seIsOpenAd"]
					_this.videoAd_id = res.data["seAdVideoId"]
					_this.interstitialAd_id = res.data["seAdInterstitialId"]
					_this.bannerAd_id = res.data["seAdBannerId"]
					_this.rewardedVideoAd_id = res.data["seAdRewardedId"]
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
						if (wx.createRewardedVideoAd) {
							rewardedVideoAd = wx.createRewardedVideoAd({
								adUnitId: _this.rewardedVideoAd_id
							})
							rewardedVideoAd.onLoad(() => {
								console.log('onLoad event emit')
							})
							rewardedVideoAd.onError((err) => {
								console.log('onError event emit', err)
							})
							rewardedVideoAd.onClose((res) => {
								// 用户点击了【关闭广告】按钮
								if (res && res.isEnded) {
									// 正常播放结束，可以下发奖励
									console.log('正常播放结束，可以下发奖励')
									_this.addUseCount()
								} else {
									// 播放中途退出，不下发奖励
									console.log('不下发奖励')
								}
							})
						}
					}
				}
			})

		},
		methods: {
			tabbarClick(e) {
				let _this = this;
				App.tabbarChange(_this.tabbarList[e].pagePath)
			},
			watchVideoAdd() {
				let _this = this;
				if (_this.isOpenAd && rewardedVideoAd) {
					rewardedVideoAd.show()
						.catch(() => {
							rewardedVideoAd.load()
								.then(() => rewardedVideoAd.show())
								.catch(err => {
									console.log('激励视频 广告显示失败')
									uni.showToast({
										icon: "none",
										title: '广告显示失败'
									})
								})
						})
				} else {
					uni.showToast({
						icon: "none",
						title: '未开通功能！请联系客服开通'
					})
				}
			},
			addUseCount() {
				let _this = this;
				App._get("/api//mine/addUserCount", {}, res => {
					_this.totalUseCount = res.data["data"]["userinfo"]["uTotalUse"];
					_this.totalSurplusCount = res.data["data"]["userinfo"]["uSysCount"]
				})
			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: $u-bg-color;
	}

	.account-head-name-id {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		width: 100%;
		height: 450rpx;
		background-image: url('https://www.myazurepicture.llgulugulu.top/api/picture/getImageByte?pid=ff53578c3d2879f6cbd9b9e32c5ff80f');
		background-repeat: no-repeat;
		background-size: cover;
	}

	.account-name,
	.account-id {
		padding-top: 10rpx;
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
	}

	.account-use-total-count {
		margin-top: 30rpx;
		padding: 0 20rpx;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}

	.account-count {
		width: 47%;
	}

	.account-total-count,
	.account-use-count {
		padding: 30rpx 0 30rpx 20rpx;
		box-shadow: 0px 0px 12px rgba(0, 0, 0, 0.1);
		border-radius: 10px;
	}

	.addUserCount {
		margin-top: 30rpx;
		padding: 0 20rpx;
	}

	.bannerAd {
		margin-top: 30rpx;
	}

	.addUserCount-cell {
		position: relative;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		padding: 30rpx 0 30rpx 20rpx;
		box-shadow: 0px 0px 12px rgba(0, 0, 0, 0.1);
		border-radius: 10px;
	}

	.addUserCount-cell:active {
		opacity: 0.4;
	}

	.kefu {
		position: absolute;
		left: 0;
		top: 0;
		width: 100%;
		height: 100%;
	}

	.kufu-button {
		opacity: 0;
		width: 100%;
		height: 100%;
	}

	.cell-rightIcon {
		padding-right: 20rpx;
	}
</style>