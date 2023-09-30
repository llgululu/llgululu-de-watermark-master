<template>
	<view class="body">
		<view class="announcement-title">
			<u--text bold align="center" size="20" :text="announcement.anTitle"></u--text>
		</view>
		<view class="announcement-tag">
			<u--text :text="'标签：'+announcement.anTag"></u--text>
		</view>
		<view class="announcement-createTime">
			<u--text :text="'创建时间：'+announcement.anCreateTime"></u--text>
		</view>
		<view class="announcement-content">
			<text decode space="emsp">{{announcement.anContent}}</text>
		</view>
		<view v-if="isOpenAd" class="bannerAd">
			<ad ad-intervals="30" :unit-id="bannerAd_id"></ad>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				isOpenAd: false,
				bannerAd_id: "",
				announcement: {
					anId: "1",
					anTag: "必看",
					anTagType: "error",
					anTitle: "元宇宙、AIGC&人工智能成功案例分享及商业模式设计",
					anContent: "  这个案例中，一家教育机构构建了一个基于元宇宙的虚拟学习环境，让学生能在其中互动、学习，甚至能体验到一些在现实生活中难以模拟的场景，如太空旅行、历史事件等。这个案例让我意识到，元宇宙不仅是一个新的娱乐平台，更有潜力成为教育、培训等领域的得力工具。\n  首先，让我们来看看元宇宙。元宇宙是一个虚拟的、三维的、互动的世界，用户可以在其中进行社交、娱乐、商业等活动。近年来，元宇宙已经在游戏、娱乐、教育等领域得到了广泛的应用。例如，著名游戏《魔兽世界》就构建了一个完整的虚拟世界，玩家可以在其中互动、竞技、社交。此外，一些教育机构也构建了元宇宙学习平台，让学生在其中进行模拟实验、角色扮演等学习活动",
					anCreateTime: "2023-08-26"
				}
			}
		},
		onReady() {
			let _this = this;
			// var reg = getRegExp('\\\\n', 'g')
			_this.announcement.anContent = _this.announcement.anContent.replace(/\\n/, '\n')
		},
		onLoad() {
			let _this = this;
			// _this.announcement.anContent = _this.format(_this.announcement.anContent)
			uni.getStorage({
				key: 'announcement',
				success: function(res) {
					// console.log(res.data);
					_this.announcement = res.data

				}
			});
			uni.getStorage({
				key: 'show_setting',
				success: function(res) {
					// console.log(res.data);
					_this.isOpenAd = res.data["seIsOpenAd"]
					_this.bannerAd_id = res.data["seAdBannerId"]
				}
			});

		}
	}
</script>

<style lang="scss">
	page {
		background-color: $u-bg-color;
	}

	.body {
		padding: 20rpx 20rpx;
	}

	.announcement-tag,
	.announcement-createTime {
		padding-top: 8rpx;
	}

	.announcement-content {
		padding-top: 8rpx;
		// text-indent: 2em;
	}

	.bannerAd {
		margin-top: 30rpx;
	}
</style>