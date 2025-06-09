# andr_autotest - icheer 产测2.0 App

这是一个基于Java语言开发的Android原生应用，使用传统XML布局和Fragment架构。

## 项目特点

- **语言**: Java
- **架构**: Fragment + Activity + MVVM
- **UI**: 传统XML布局（不使用Jetpack Compose）
- **最低SDK**: API 24 (Android 7.0)
- **目标SDK**: API 34 (Android 14)

## 架构说明
项目采用MVVM架构设计，
具体包含以下几个层次：

### MVVM架构层次

#### View层
- **Activity**: 作为Fragment的容器，负责界面导航和生命周期管理
- **Fragment**: 具体的UI界面实现，使用XML布局文件
- **ViewBinding**: 用于获取XML中的UI组件，替代findViewById，提高性能和类型安全

#### ViewModel层
- **AndroidViewModel**: 继承自AndroidViewModel，持有Application上下文
- **LiveData**: 用于数据观察和UI更新，确保UI状态与数据同步
- **业务逻辑处理**: 处理用户交互，调用Repository层获取数据

#### Model层（数据层）
- **Repository**: 数据仓库模式，统一数据访问接口
- **Retrofit**: RESTful API网络请求框架
- **RxJava**: 响应式编程，处理异步操作和数据流
- **本地数据存储**: SharedPreferences、SQLite等

#### 架构优势
- **分离关注点**: UI逻辑与业务逻辑分离，便于维护和测试
- **数据驱动**: 使用LiveData实现响应式UI更新
- **异步处理**: RxJava处理网络请求和耗时操作
- **类型安全**: ViewBinding提供编译时类型检查


### 项目结构
```
app/
├── src/
│   ├── main/
│   │   ├── java/com/icheer/autotest/together/
│   │   │   ├── ui/                                   # UI层
│   │   │   │   ├── splash/                           # 启动页面模块
│   │   │   │   │   ├── view/                         # Fragment实现
│   │   │   │   │   │   └── SplashFragment.java
│   │   │   │   │   └── viewmodel/                    # ViewModel层
│   │   │   │   │       └── SplashViewModel.java
│   │   │   │   ├── home/                             # 首页模块
│   │   │   │   │   ├── view/
│   │   │   │   │   │   └── HomeFragment.java
│   │   │   │   │   ├── viewmodel/
│   │   │   │   │   │   └── HomeViewModel.java
│   │   │   ├── data/                                 # 数据层
│   │   │   │   ├── repository/                       # 数据仓库
│   │   │   │   │   ├── splash/
│   │   │   │   │   │   └── SplashRepository.java
│   │   │   │   │   ├── home/
│   │   │   │   │   │   └── HomeRepository.java
│   │   │   │   ├── network/                          # 网络数据源
│   │   │   │   │   ├── ApiService.java               # API接口定义
│   │   │   │   └── model/                            # 数据模型
│   │   │   ├── utils/                                # 工具类
│   │   │   ├── common/                               # 公共组件
│   │   │   └── MainActivity.java                     # 主Activity
│   │   ├── res/                                      # 资源文件
│   │   │   ├── layout/                               # 布局文件
│   │   │   │   ├── activity_main.xml
│   │   │   │   ├── activity_splash.xml
│   │   │   │   ├── fragment_home.xml
│   │   │   │   └── item_*.xml                        # 列表项布局
│   │   │   ├── values/                               # 值资源
│   │   │   │   ├── strings.xml                       # 字符串资源
│   │   │   │   ├── colors.xml                        # 颜色资源
│   │   │   │   ├── dimens.xml                        # 尺寸资源
│   │   │   │   ├── styles.xml                        # 样式资源
│   │   │   │   └── themes.xml                        # 主题资源
│   │   │   ├── values-night/                         # 夜间模式资源
│   │   │   │   ├── colors.xml
│   │   │   │   └── themes.xml
│   │   │   ├── drawable/                             # 图片资源
│   │   │   │   ├── ic_*.xml                          # 矢量图标
│   │   │   │   └── selector_*.xml                    # 选择器
│   │   │   ├── menu/                                 # 菜单资源
│   │   │   │   └── bottom_nav_menu.xml
│   │   │   └── xml/                                  # XML配置
│   │   │       ├── network_security_config.xml
│   │   │       └── backup_rules.xml
│   │   └── AndroidManifest.xml                       # 应用清单文件
├── build.gradle                                      # 模块级构建配置
├── proguard-rules.pro                                # 代码混淆规则
└── gradle.properties                                 # Gradle属性配置

```

### Fragment架构

1. **MainActivity**: 作为Fragment容器
2. **生命周期管理**: 每个Fragment独立管理自己的生命周期
3. **内存优化**: Fragment复用，避免重复创建

### 页面功能

#### 1.  (SplashFragment)
- 欢迎界面

#### 2.  (AuthFragment)
- 登录界面

#### 3. 首页 (HomeFragment)
- App主界面

#### 4. 设置 (SettingsFragment)
- 设置界面

## 构建和运行

### 环境要求
- Android Studio Arctic Fox或更高版本
- JDK 8或更高版本
- Android SDK API 24-34
- Gradle 8.0+

### 构建步骤
1. 使用Android Studio打开项目
2. 等待Gradle同步完成
3. 连接Android设备或启动模拟器
4. 点击Run按钮或使用快捷键Shift+F10

### 构建命令
```bash
# 构建Debug版本
./gradlew assembleDebug

# 构建Release版本
./gradlew assembleRelease

# 运行测试
./gradlew test

# 安装到设备
./gradlew installDebug
```

## 开发规范

### 命名规范
- 类名: 使用大驼峰命名法 (PascalCase)
- 方法名: 使用小驼峰命名法 (camelCase)  
- 变量名: 使用小驼峰命名法 (camelCase)
- 常量名: 使用全大写下划线分隔 (UPPER_SNAKE_CASE)
- 资源ID: 使用下划线分隔 (snake_case)

---

**开发者**: icheer 团队  
**版本**: 1.0.0  
**更新时间**: 2025年6月 