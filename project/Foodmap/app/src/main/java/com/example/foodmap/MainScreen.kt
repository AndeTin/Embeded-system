package com.example.foodmap

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val restaurants = remember {
        mutableStateOf(
            listOf(
                Restaurant("旺角迷你石頭火鍋", "新北市241三重區正義南路2-1號", "火鍋"),
                Restaurant("金山肉包王", "新北市208金山區中山路237號", "中式點心"),
                Restaurant("泰味廚房", "新北市220板橋區中山路二段42號", "泰式料理"),
                Restaurant("黑師傅捲心酥", "新北市242新莊區頭前路19號", "伴手禮"),
                Restaurant("郭金山花生店", "新北市224瑞芳區基山街30號", "伴手禮"),
                Restaurant("三姐妹阿給", "新北市251淡水區真理街2巷1號", "小吃"),
                Restaurant("淡水可口魚丸店‎", "新北市251淡水區中正路232號‎", "小吃"),
                Restaurant("阿妃健康廚房(生食)", "新北市234永和區中正路470號", "水餃"),
                Restaurant("蜜而可藝術蛋糕", "新北市242新莊區建福路62號", "麵包蛋糕"),
                Restaurant("菁桐老街紅寶礦工食堂", "新北市226平溪區菁桐老街58號", "懷舊料理"),
                Restaurant("特香齋西餐廳", "新北市220板橋區中山路一段26號2樓", "西餐"),
                Restaurant("Roliya(羅莉亞)", "新北市231新店區民權路42巷59弄12號", "義式料理"),
                Restaurant("紫藤花藝村", "新北市249八里區荖阡坑路6鄰69-1號", "中式料理"),
                Restaurant("海堤竹筍餐廳", "新北市249八里區觀海大道28號", "海鮮"),
                Restaurant("福昂西咖啡養生料理餐", "新北市242新莊區新泰路123巷4號", "日式料理"),
                Restaurant("康喜軒牛角麵包總公司", "新北市239鶯歌區鶯歌區鶯桃路53號", "伴手禮"),
                Restaurant("呷冰站", "新北市237三峽區國際一街82號", "冰品"),
                Restaurant("帕爾堤麵包蛋糕", "新北市247蘆洲區長榮路211巷5號", "麵包蛋糕"),
                Restaurant("厚道飲食店", "新北市239鶯歌區尖山埔路79號 ", "懷舊料理"),
                Restaurant("東道飲食亭", "新北市237三峽區仁愛街7號", "懷舊料理"),
                Restaurant("豆花工房", "新北市231新店區光明街272號", "甜點"),
                Restaurant("亞米小鎮", "新北市247蘆洲區八里區觀海大道29號", "西餐"),
                Restaurant("郭全福餅店", "新北市251淡水區中山北路一段61號", "伴手禮"),
                Restaurant("美觀園海鮮餐廳(美觀園飯店)", "新北市207萬里區野柳里港東路156號", "海鮮"),
                Restaurant("福州兩相好", "新北市249八里區米倉里渡船頭街30號", "小吃"),
                Restaurant("阿水獅豬腳大王", "新北市234永和區永貞路186號", "豬腳"),
                Restaurant("二號倉庫咖啡", "新北市252三芝區中山路一段4號(三芝農會旁)", "咖啡"),
                Restaurant("么妹農產品", "新北市224瑞芳區九份基山街169號", "伴手禮"),
                Restaurant("匯‧鮨‧割烹", "新北市234永和區永貞路303號", "日式料理"),
                Restaurant("銘鄉鐵板燒", "新北市242新莊區中平路34號", "鐵板燒"),
                Restaurant("客滿燒烤", "新北市235中和區景平路678號", "燒烤"),
                Restaurant("八里亭麵館", "新北市234永和區中山路一段293號1樓", "麵食"),
                Restaurant("黃金海岸活蝦之家", "新北市234永和區福和路182號", "海鮮"),
                Restaurant("永力旺德國豬腳", "新北市234永和區永和路二段304號", "德國料理"),
                Restaurant("建香四姐妹(建香海鮮料理店)", "新北市207萬里區野柳里港東路160號", "海鮮"),
                Restaurant("那年夏天咖啡館", "新北市252三芝區後厝里北勢子45-13號", "咖啡"),
                Restaurant("醍醐大師(淡江店)", "新北市251淡水區中正路11巷8號", "伴手禮"),
                Restaurant("佘家孔雀蛤大王", "新北市251淡水區中正路55號地下一樓", "海鮮"),
                Restaurant("老爹滷味", "新北市234永和區福和路326號", "滷味"),
                Restaurant("山中傳奇", "新北市222深坑區風格街51號", "中式料理"),
                Restaurant("萬吉豆腐老店", "新北市222深坑區深坑街122號", "豆腐料理"),
                Restaurant("高家冰溫泉蛋創始店", "新北市233烏來區烏來街135號", "小吃"),
                Restaurant("小竹屋日式小吃", "新北市234永和區文化路19號1F", "日式料理"),
                Restaurant("布蕾派對", "新北市243泰山區新北大道七段15巷1號", "甜點"),
                Restaurant("靴子義大利麵餐坊", "新北市242新莊區中正路593號", "義式料理"),
                Restaurant("星星牛排館(御品餐廳)", "新北市251淡水區中正路11-4號", "牛排"),
                Restaurant("阿城鵝肉", "新北市236土城區清水路78號", "鵝肉"),
                Restaurant("麻吉奶奶鮮奶麻糬", "新北市251淡水區中正路220號", "甜點"),
                Restaurant("醍醐味便當", "新北市234永和區安樂路304巷28號", "便當"),
                Restaurant("一回生二回熟", "新北市231新店區中央五街6號", "火鍋"),
                Restaurant("海豚Cafe", "新北市252三芝區北勢子42-11號二樓", "咖啡"),
                Restaurant("藍色愛琴海", "新北市252三芝區北勢子12號之9", "咖啡"),
                Restaurant("媽咪里啦手工餅乾", "新北市234永和區福和路229號-2", "伴手禮"),
                Restaurant("濠記食坊", "新北市235中和區宜安路55號", "小吃"),
                Restaurant("八八八海鮮", "新北市252三芝區中興街2段30號之1", "海鮮"),
                Restaurant("意芳海鮮餐廳(意芳飯店)", "新北市207萬里區野柳里港東路155號", "海鮮"),
                Restaurant("女皇餐廳(女皇海鮮餐廳)", "新北市207萬里區野柳里港東路163號", "海鮮"),
                Restaurant("舜德農莊休閒餐廳", "新北市222深坑區文山路一段62巷35號", "中式料理"),
                Restaurant("唯力香食品有限公司", "新北市237三峽區嘉添里181-32號", "伴手禮"),
                Restaurant("鹿港甘仔店懷舊餐廳", "新北市236土城區廣興街14號", "懷舊料理"),
                Restaurant("鹿鶴園蔬食養生館", "新北市236土城區承天路92-5號", "蔬食"),
                Restaurant("大團圓餐廳", "新北市222深坑區阿柔里25-1號", "中式料理"),
                Restaurant("劉安平古早味什錦麵", "新北市235中和區安平路128號", "麵食"),
                Restaurant("雅米早午餐", "新北市220板橋區陽明街28號", "早午餐"),
                Restaurant("美養莊園餐飲", "新北市231新店區二十張路11巷1-1號", "養生料理"),
                Restaurant("義麵寶寶義大利麵", "新北市235中和區圓通路198巷2號1樓", "義式料理"),
                Restaurant("國光饅頭", "新北市235中和區連城路330號", "中式點心"),
                Restaurant("咖啡走廊", "新北市231新店區中央五街5號", "咖啡"),
                Restaurant("山間倉房", "新北市235中和區興南路二段399巷124號-1", "蔬食"),
                Restaurant("惠香嘉義火雞肉飯", "新北市236土城區中央路二段264號", "小吃"),
                Restaurant("名根烤肉食材宅配外送", "新北市231新店區柴埕路60巷2號", "烤肉"),
                Restaurant("小樂天餃子館", "新北市231新店區民族路14號", "水餃"),
                Restaurant("Gino Pizza店", "新北市247蘆洲區長安街108巷27號", "披薩"),
                Restaurant("香榭咖啡屋", "新北市220板橋區大勇街28號", "咖啡"),
                Restaurant("老店淡水魚丸", "新北市251淡水區中正路135-2號", "小吃"),
                Restaurant("E61咖啡場所", "新北市234永和區安樂路200號", "咖啡"),
                Restaurant("悲情城市", "新北市224瑞芳區豎崎路35號2樓", "茶坊"),
                Restaurant("好茶饌(原順益茶館)", "新北市232坪林區北宜路八段216號", "茶坊"),
                Restaurant("深坑麗芬肉粽", "新北市222深坑區深坑街80號", "肉粽"),
                Restaurant("嘉義閣小吃店", "新北市222深坑區深坑街58號", "小吃"),
                Restaurant("彰鶯肉圓", "新北市239鶯歌區行政路35號", "肉圓"),
                Restaurant("富貴陶園", "新北市239鶯歌區重慶街96~98號", "中式料理"),
                Restaurant("不一樣燒臘店", "新北市251淡水區北新路182巷27號", "燒臘"),
                Restaurant("戲夢人生茶飯館", "新北市224瑞芳區豎崎路13號", "茶坊"),
                Restaurant("芋仔蕃薯(芋仔蕃薯茶坊)", "新北市224瑞芳區崇文里市下巷18號", "茶坊"),
                Restaurant("九份老麵店", "新北市224瑞芳區基山街45號", "麵食"),
                Restaurant("阿娟小吃", "新北市227雙溪區中華路8號", "小吃"),
                Restaurant("阿媽的酸梅湯", "新北市251淡水區中正路135-1號", "飲料"),
                Restaurant("88號水碼頭活海鮮餐廳", "新北市208金山區豐漁里民生路88號", "海鮮"),
                Restaurant("烏來小吃店", "新北市233烏來區烏來街91號", "小吃"),
                Restaurant("原住民泰雅婆婆美食店", "新北市233烏來區烏來里烏來街14號", "原住民料理"),
                Restaurant("姊妹雙胞胎", "新北市249八里區渡船頭街25號", "小吃"),
                Restaurant("普園北平館", "新北市235中和區中和路362號2樓", "中式料理"),
                Restaurant("合歡茶宴風味餐廳", "新北市232坪林區水德里水聳淒坑28號", "茶餐廳")
            )
        )
    }

    val routePlans = remember { mutableStateOf(emptyList<RoutePlan>()) }

    val favoriteRestaurants = restaurants.value.filter { it.isFavorite }

    fun toggleFavorite(restaurant: Restaurant) {
        val index = restaurants.value.indexOf(restaurant)
        if (index != -1) {
            val updatedRestaurant = restaurant.copy(isFavorite = !restaurant.isFavorite)
            val updatedList = restaurants.value.toMutableList()
            updatedList[index] = updatedRestaurant
            restaurants.value = updatedList
        }
    }

    fun addRestaurantToPlan(restaurant: Restaurant, planName: String) {
        val plan = routePlans.value.find { it.name == planName }
        if (plan != null) {
            if (plan.restaurants.contains(restaurant)) return // Avoid duplicates
            val updatedPlan = plan.copy(restaurants = plan.restaurants + restaurant)
            val updatedPlans = routePlans.value.toMutableList()
            val planIndex = updatedPlans.indexOf(plan)
            updatedPlans[planIndex] = updatedPlan
            routePlans.value = updatedPlans
        } else {
            val newPlan = RoutePlan(planName, listOf(restaurant))
            routePlans.value = routePlans.value + newPlan
        }
    }

    fun removeRestaurantFromPlan(restaurant: Restaurant, plan: RoutePlan) {
        val updatedRestaurants = plan.restaurants.toMutableList().apply { remove(restaurant) }
        val updatedPlan = plan.copy(restaurants = updatedRestaurants)
        val updatedPlans = routePlans.value.toMutableList()
        val planIndex = updatedPlans.indexOfFirst { it.name == plan.name }
        if (planIndex != -1) {
            if (updatedRestaurants.isEmpty()) {
                updatedPlans.removeAt(planIndex)
            } else {
                updatedPlans[planIndex] = updatedPlan
            }
            routePlans.value = updatedPlans
        }
    }

    fun reorderRestaurantsInPlan(plan: RoutePlan, from: Int, to: Int) {
        if (from == to) return
        val currentList = plan.restaurants.toMutableList()
        val itemToMove = currentList.removeAt(from)
        currentList.add(to, itemToMove)

        val updatedPlan = plan.copy(restaurants = currentList)
        val updatedPlans = routePlans.value.toMutableList()
        val planIndex = updatedPlans.indexOfFirst { it.name == plan.name }
        if (planIndex != -1) {
            updatedPlans[planIndex] = updatedPlan
            routePlans.value = updatedPlans
        }
    }

    Scaffold(
        bottomBar = {
            BottomAppBar {
                IconButton(onClick = { navController.navigate("search") }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
                IconButton(onClick = { navController.navigate("favorites") }) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Favorites")
                }
                IconButton(onClick = { navController.navigate("route") }) {
                    Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Route Plan")
                }
                IconButton(onClick = { (context as? Activity)?.finish() }) {
                    Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Exit")
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = "search", Modifier.padding(innerPadding)) {
            composable("search") { 
                SearchScreen(
                    restaurants = restaurants.value,
                    onFavoriteClick = ::toggleFavorite
                )
            }
            composable("favorites") {
                FavoritesScreen(
                    favoriteRestaurants = favoriteRestaurants,
                    routePlans = routePlans.value,
                    onAddRestaurantToPlan = ::addRestaurantToPlan,
                    onFavoriteClick = ::toggleFavorite
                )
            }
            composable("route") {
                RoutePlanScreen(
                    routePlans = routePlans.value,
                    onRemoveRestaurant = ::removeRestaurantFromPlan,
                    onReorder = ::reorderRestaurantsInPlan
                )
            }
        }
    }
}