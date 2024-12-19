# Kotlin Cold Flow와 Hot Flow에 대한 설명
Kotlin Coroutines에서 Flow는 데이터 스트림을 비동기적으로 처리하는 데 사용됩니다. Flow는 Cold Flow와 Hot Flow로 나뉘며, 각각의 특징과 사용 시점이 다릅니다.
### Cold Flow
* 정의: Cold Flow는 수집(collect)될 때마다 새로운 데이터 스트림을 생성하는 Flow입니다. 즉, 각각의 수집자(collector)는 독립적인 데이터 스트림을 받게 됩니다.
* 특징:
  * 주문형(On-demand): 수집이 시작될 때만 데이터를 방출합니다.
  * 재생 가능(Replayable): 여러 번 수집해도 동일한 데이터를 방출합니다.
  * 상태 비공유(Stateless): 각 수집자는 Flow의 상태를 공유하지 않습니다.

* 사용 시점:
  * 네트워크 요청 결과 처리
  * 파일 읽기
  * 데이터베이스 쿼리 결과 처리
  * 일회성 이벤트 처리
   
### Hot Flow
* 정의: Hot Flow는 수집 여부와 관계없이 데이터를 방출하는 Flow입니다. 즉, 모든 수집자는 동일한 데이터 스트림을 공유합니다.
* 특징:
  * 지속적(Continuous): 수집 여부와 관계없이 데이터를 방출합니다.
  * 재생 불가능(Non-replayable):  수집 시점에 따라 받는 데이터가 다를 수 있습니다.
  * 상태 공유(Stateful): 모든 수집자는 Flow의 상태를 공유합니다.

* 사용 시점:
  * UI 상태 업데이트
  * 실시간 이벤트 처리
  * 공유 리소스 관리
  * 여러 곳에서 동일한 데이터를 관찰해야 할 때

# StateFlow와 SharedFlow에 대한 자세한 설명
StateFlow와 SharedFlow는 Kotlin Coroutines에서 제공하는 Hot Flow로, 값을 방출하고 여러 수집기에서 공유할 수 있도록 설계되었습니다. 두 Flow 모두 상태를 가지고 있으며, 값이 업데이트될 때마다 수집기에 새로운 값을 방출합니다. 하지만 몇 가지 중요한 차이점이 있습니다.
### StateFlow
* 상태 홀더: StateFlow는 현재 상태를 나타내는 단일 값을 저장하는 Flow입니다. UI 상태, 데이터 로딩 상태 등 앱의 현재 상태를 나타내는 데 적합합니다.
* 초기값 필수: StateFlow는 생성 시 초기값을 필수로 제공해야 합니다.
* 최신 값 방출: 새로운 수집기가 StateFlow를 수집하기 시작하면 가장 최신 값을 즉시 방출합니다.
* Conflation: StateFlow는 Conflation을 지원합니다. 이는 값이 빠르게 업데이트될 때 수집기가 이전 값을 놓치지 않도록 가장 최신 값만 방출하는 기능입니다.
* 사용 예시:
  * UI 상태 관리 (예: 현재 화면, 로딩 상태, 오류 메시지)
  * 데이터베이스에서 가져온 최신 데이터 표시
  * ViewModel에서 UI로 데이터 전달

### SharedFlow
* 이벤트 스트림: SharedFlow는 여러 이벤트를 방출하는 Flow입니다. 클릭 이벤트, 네트워크 응답, 데이터베이스 변경 사항 등 일련의 이벤트를 처리하는 데 적합합니다.
* 초기값 선택: SharedFlow는 생성 시 초기값을 제공하지 않아도 됩니다.
* Replay: SharedFlow는 Replay 기능을 제공합니다. 이는 새로운 수집기가 수집을 시작할 때 지정된 개수만큼의 이전 값을 재생하는 기능입니다.
* Buffer Overflow: SharedFlow는 버퍼 오버플로 처리 전략을 설정할 수 있습니다. 버퍼가 가득 차면 가장 오래된 값을 삭제하거나, 새로운 값을 무시하거나, 예외를 발생시키도록 설정할 수 있습니다.
* 사용 예시:
  * 버튼 클릭 이벤트 처리
  * 네트워크 응답 처리
  * 데이터베이스 변경 사항 감지
  * ViewModel에서 다른 ViewModel로 이벤트 전달

# Android ViewModel 관련
### owner
owner는 특정 객체 또는 리소스에 대한 소유권을 나타내는 개념입니다. 소유권은 객체의 생명 주기와 밀접하게 관련되어 있으며, 객체의 생성, 사용, 소멸을 관리하는 데 중요한 역할을 합니다.
* Owner의 역할
  * 생명 주기 관리: owner는 자신이 소유한 객체의 생명 주기를 관리합니다. owner가 소멸될 때, 소유된 객체도 함께 소멸됩니다. 이를 통해 메모리 누수를 방지하고 리소스를 효율적으로 관리할 수 있습니다.
  * 책임 분담: owner는 자신이 소유한 객체에 대한 책임을 집니다. 객체의 상태 변경, 이벤트 처리, 리소스 해제 등을 담당합니다.
  * 상태 일관성 유지: owner는 자신이 소유한 객체의 상태를 일관되게 유지합니다. 객체의 상태 변경은 owner를 통해 이루어지며, 다른 객체가 직접 상태를 변경하는 것을 방지합니다.

### Compose Navigation과 Dagger Hilt를 함께 사용할 때 hiltViewModel()의 owner 설정
1. NavController.currentBackStackEntry 사용: 특정 그래프의 NavBackStackEntry를 가져와 owner로 전달합니다.
```kotlin
val navController = rememberNavController()
val navBackStackEntry = navController.currentBackStackEntryAsState().value

val viewModel: YourViewModel = hiltViewModel(navBackStackEntry)
```
2. NavHost에서 NavBackStackEntry 가져오기: NavController를 사용하여 명시적으로 owner를 설정합니다.
```kotlin
NavHost(navController = navController, startDestination = "main") {
    composable("main") {
        val parentEntry = remember(navController) { navController.getBackStackEntry("main") }
        val viewModel: YourViewModel = hiltViewModel(parentEntry)
        MainScreen(viewModel)
    }
}
```
3. Scoped ViewModel in Nested Graph: 하위 네비게이션 그래프에서 상위 ViewModel과 공유하려는 경우 getBackStackEntry를 통해 명시적으로 owner를 설정합니다.
```kotlin
NavHost(navController = navController, startDestination = "parent") {
    navigation(startDestination = "child", route = "parent") {
        composable("child") {
            val parentEntry = remember(navController) { navController.getBackStackEntry("parent") }
            val viewModel: SharedViewModel = hiltViewModel(parentEntry)
            ChildScreen(viewModel)
        }
    }
}
```
# Paging3 관련
### PagingSource와 getRefreshKey 함수에 대한 자세한 설명
PagingSource는 Paging 3 라이브러리에서 데이터를 로드하는 핵심 구성 요소입니다. getRefreshKey 함수는 데이터 새로 고침 시 Paging 라이브러리가 데이터를 어디서부터 로드해야 할지 결정하는 데 사용됩니다.

### PagingSource의 load 함수: 파라미터와 리턴 값 자세한 설명
* load 함수의 시그니처
```kotlin
  abstract suspend fun load(params: LoadParams<Key>): LoadResult<Key, Value>
```

#### 파라미터: LoadParams<Key>
1. key: Key?
* 다음 데이터를 로드하는 데 필요한 키입니다.
* null인 경우는 첫 로드를 의미합니다 (초기 데이터 요청).
* 각 페이지 로드 후 반환한 LoadResult.Page에서 지정한 nextKey와 prevKey를 기반으로 설정됩니다.
* 일반적으로:
   * 첫 요청: null
   * 다음 페이지 요청: 이전 LoadResult.Page에서 반환한 nextKey
   * 이전 페이지 요청: 이전 LoadResult.Page에서 반환한 prevKey

2. loadSize: Int
* 요청할 항목의 개수입니다.
* Paging 라이브러리가 자동으로 결정하며, 보통 PagingConfig에서 설정된 페이지 크기를 기반으로 합니다.

3. placeholdersEnabled: Boolean
* 자리 표시자(Placeholders)를 사용할지 여부를 나타냅니다.
* 자리 표시자가 활성화되면 데이터가 없는 경우에도 RecyclerView에서 빈 항목 공간을 유지할 수 있습니다.

#### 리턴 값: LoadResult<Key, Value>
1. LoadResult.Page
* 데이터를 성공적으로 로드했을 때 반환합니다.

2. LoadResult.Error
* 데이터를 로드하는 중 오류가 발생했을 때 반환합니다.
