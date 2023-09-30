<template>
	<view class="content">
		<view class="swiper">
			<u-swiper :indicator="true" radius="10" height="140" keyName="swUrl" :list="swiperList"></u-swiper>
		</view>
		<view class="u-grid">
			<view class="u-grid-item">
				<view class="u-grid-item-icon-text" v-for="(item,index) in gridList" :key="index">
					<view class="u-grid-item-icon">
						<u-icon :color="item.color" size="32" :name="item.name"></u-icon>
					</view>
					<view class="u-grid-item-text">
						<u--text :text="item.title"></u--text>
					</view>
					<view v-if="item.openType!==''" class="u-grid-item-button">
						<button :open-type="item.openType" class="u-grid-item-button-button"></button>
					</view>
					<view v-if="item.isJumpPage" class="u-grid-item-button">
						<button @click="jumpPage(item.jumpPagePath)" class="u-grid-item-button-button"></button>
					</view>
					<view v-if="item.isEvaluation" class="u-grid-item-button">
						<button @click="evaluation" class="u-grid-item-button-button"></button>
					</view>
					<view v-if="item.isJumpOther" class="u-grid-item-button">
						<button @click="jumpOther(item.jumpAppid,item.jumpAppPath)"
							class="u-grid-item-button-button"></button>
					</view>
				</view>
			</view>
		</view>
		<view class="announcement">
			<view class="announcement-title">
				<u--text bold size="20" text="最新通告"></u--text>
			</view>
			<view class="announcement-body">
				<view v-if="announcementList.length==0" class="announcement-no-data">
					<u--text align="center" bold size="16" text="暂无通告..."></u--text>
				</view>
				<view v-for="(item,index) in announcementList" :key="item.id" class="announcement-content">
					<view class="announcement-content-tag-title">
						<view v-if="item.anTag!==''" class="announcement-content-tag">
							<u-tag :text="item.anTag" plain :type="item.anTagType" shape="circle"></u-tag>
						</view>
						<view class="announcement-content-title">
							<u--text lines="1" bold size="17" :text="item.anTitle"></u--text>
						</view>
					</view>
					<view class="announcement-content-contents">
						<u--text size="15" lines="3" :text="item.anContent"></u--text>
					</view>
					<u-line></u-line>
					<view class="announcement-content-createTime-button">
						<view class="announcement-content-createTime">
							<u-icon space="6" labelSize="14" size="15" :label="item.anCreateTime" name="clock"></u-icon>
						</view>
						<view class="announcement-content-button">
							<u-button @click="goAnnouncement(index)" size="small" type="primary" shape="circle"
								text="去看看"></u-button>
						</view>
					</view>

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
				tabbarIndex: 0,
				swiperList: [{
					swUrl: "https://www.myazurepicture.llgulugulu.top/api/picture/getImageByte?pid=a64445c32859174db59ac67bfd5b1a32"
				}],
				gridList: [{
						name: 'file-text',
						title: '介绍',
						color: '#ff0000',
						openType: "",
						isEvaluation: false,
						isJumpOther: false,
						isJumpPage: true,
						jumpAppid: "",
						jumpAppPath: "",
						jumpPagePath: "/pages/index/introduction"
					},
					{
						name: 'server-man',
						title: '客服',
						color: '#f4ea2a',
						openType: "contact",
						isEvaluation: false,
						isJumpOther: false,
						isJumpPage: false,
						jumpAppid: "",
						jumpAppPath: "",
						jumpPagePath: ""
					},
					{
						name: 'photo',
						title: '壁纸',
						color: '#ff55ff',
						openType: "",
						isEvaluation: false,
						isJumpOther: true,
						isJumpPage: false,
						jumpAppid: "wxb3f3ce174895b5a1",
						jumpAppPath: "pages/tabbar",
						jumpPagePath: ""
					},
					{
						name: 'edit-pen',
						title: '评价',
						color: '#1afa29',
						openType: "",
						isEvaluation: true,
						isJumpOther: false,
						isJumpPage: false,
						jumpAppid: "",
						jumpAppPath: "",
						jumpPagePath: ""
					},
					{
						name: 'share-square',
						title: '分享',
						color: '#1296db',
						openType: "share",
						isEvaluation: false,
						isJumpOther: false,
						isJumpPage: false,
						jumpAppid: "",
						jumpAppPath: "",
						jumpPagePath: ""
					}
				],
				announcementList: [],
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
				]
			}
		},
		onLoad() {
			let _this = this;

			App._get("/api/index/getSetting", {}, res => {
				_this.swiperList = res.data["data"]["swipeList"]
				_this.announcementList = res.data["data"]["announcementList"]
				uni.setStorageSync("show_setting", res.data["data"]["setting"])
				_this.isOpenAd = res.data["data"]["setting"]["seIsOpenAd"]
				_this.videoAd_id = res.data["data"]["setting"]["seAdVideoId"]
				_this.interstitialAd_id = res.data["data"]["setting"]["seAdInterstitialId"]
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
			})
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
			tabbarClick(e) {
				let _this = this;
				App.tabbarChange(_this.tabbarList[e].pagePath)
			},
			evaluation() {
				var plugin = requirePlugin("wxacommentplugin");
				plugin.openComment({
					success: (res) => {
						console.log('plugin.openComment success', res)
					},
					fail: (res) => {
						console.log('plugin.openComment fail', res)
					}
				})
			},
			jumpOther(appid, path) {
				uni.navigateToMiniProgram({
					appId: appid,
					path: path,
					success(res) {
						// 打开成功
						console.log('打开成功')
					}
				})
			},
			goAnnouncement(index) {
				let _this = this;
				uni.setStorage({
					key: "announcement",
					data: _this.announcementList[index],
					success: function() {
						// console.log('success');
						_this.jumpPage("/pages/index/announcemen")
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

	.swiper {
		padding: 10rpx 20rpx 20rpx 20rpx;
	}

	.u-grid-item {
		display: flex;
		flex-direction: row;
	}

	.u-grid-item-icon-text {
		position: relative;
		width: 20%;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}

	.u-grid-item-button {
		width: 100%;
		height: 100%;
		opacity: 0;
		position: absolute;
		top: 0;
		left: 0;
	}

	.u-grid-item-button-button {
		height: 100%;
		width: 100%;
	}

	.u-grid-item-icon-text:hover {
		opacity: 0.5;
	}

	.u-grid-item-icon,
	.u-grid-item-text {
		padding-top: 10rpx;
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
	}

	.announcement {
		padding: 20rpx 20rpx;
	}

	.announcement-no-data {

		margin-top: 20rpx;
		padding: 50rpx 20rpx;
		border-radius: 25rpx;
		background-color: #f8f8f8;
		box-shadow: 0px 5px 5px 0px #EDEDED;
	}

	.announcement-content {
		margin-top: 20rpx;
		padding: 20rpx 20rpx;
		border-radius: 25rpx;
		background-color: #f8f8f8;
		box-shadow: 0px 5px 5px 0px #EDEDED;
	}

	.announcement-content-tag-title {
		display: flex;
	}

	.announcement-content-title {
		display: flex;
		justify-content: center;
		align-items: center;
		padding-left: 10rpx;
	}

	.announcement-content-tag {
		display: flex;
		justify-content: center;
		align-items: center;
		padding-right: 20rpx;
	}

	.announcement-content-contents {
		padding: 10rpx 10rpx 20rpx 10rpx;
	}

	.announcement-content-createTime-button {
		padding-top: 20rpx;
		display: flex;
		justify-content: space-between;
	}

	.ad-show {
		width: 100%;
		padding-top: 10rpx;
	}

	.announcement-content-createTime,
	.announcement-content-button {
		display: flex;
		justify-content: center;
		align-items: center;
	}
</style>