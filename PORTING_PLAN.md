# PasterDream Fabric 1.21.1 七阶段移植计划

## 第一阶段：工程骨架与启动门禁（已完成）

- 接入 Fabric API、Gemini、CuriosFabric、GeckoLib、Player Animator。
- GeckoLib 与 Player Animator 使用 Fabric JIJ 内置。
- Gemini 与 CuriosFabric 作为外置前置，不打进模组。
- 拆分公共端与客户端入口，暂时排除 JEI 等联动源码。

验收：Gradle 配置通过，公共端和客户端最小入口可以启动。

## 第二阶段：注册表与静态内容（已完成）

- 保持全部 `pasterdream:*` 注册 ID 不变。
- 重写方块、物品、方块实体、实体、效果、声音、粒子、流体、菜单、配方类型和创造标签。
- 建立统一的 ID 目录、Gemini 字段扫描容器和资源路径。
- 记录后续需要替换为专用行为类的内容。

验收：每类静态内容都能注册，资源引用完整，复杂行为留待后续阶段。

当前实现：334 个方块、332 个 BlockItem、392 个独立物品、51 个实体、79 个方块实体，以及全部效果、声音、粒子、流体、菜单和配方类型均通过 Gemini 字段扫描容器注册。旧 ID 映射见 `docs/item-id-renames.tsv` 和 `docs/block-id-renames.tsv`。

## 第三阶段：持久状态、配置与网络（已完成）

- 用 Gemini Components 和原版数据组件取代 Forge Capability、持久 NBT 和物品 NBT。
- 重建精神值、融梦能量、冷却和玩家重生复制。
- Forge 配置迁移到 Gemini TOML 配置。
- `SimpleChannel` 迁移到 Gemini 类型化网络与 bundle。

验收：状态经过存档、重登、死亡重生和跨维度后保持正确。

当前实现：SAN、融梦能量和冷却均使用 Gemini 玩家组件；SAN 与融梦能量使用 `AutoSyncedComponent`，通过 Gemini 原生组件同步传输。公共端与客户端配置使用 Gemini TOML 注解系统，主网络 channel 使用 GeminiNetwork。物品内库存和菜单消息留到第五阶段。

## 第四阶段：核心玩法逻辑与数据资源层（首版基础已完成，语义回归进行中）

- Forge 事件迁移到 Gemini 事件、Fabric 回调或窄范围 Mixin。
- 将物品耐久、食物、工具和属性迁移到 1.21.1 数据组件。
- 重建属性、伤害、治疗、药水效果、过程、配方、战利品和爆炸逻辑。
- 将重复的 MCR 过程模板收敛为领域级辅助代码。

验收：战斗、生存、进度和机器核心逻辑双端运行，状态修改由服务端负责。

当前实现：

- 玩家攻击、受击闪避、易伤、治疗倍率、SAN 环境变化、低 SAN 效果、重生 SAN、QYM 装备效果和瞬身请求均使用 Gemini 事件、Gemini 组件或 Fabric 原生回调，不保留 Forge 事件总线。
- `shadow_blasting` 配方改为 1.21.1 的 `MapCodec` 和 `StreamCodec`，输入统一为原版 `SingleRecipeInput`，序列化器由 Gemini 字段扫描注册。
- 食物、堆叠数、稀有度、工具、盔甲、耐久、修复材料、属性修饰和不可破坏标记由 1.21.1 物品组件及 `ArmorMaterial` 注册表生成；47 个工具和 23 件可穿戴物品不再保留 MCR 匿名材质类。
- 玩家属性补入原版玩家属性表；旧药水的属性修饰统一由效果工厂配置，瞬时 SAN / 融梦能量效果由原版状态效果生命周期执行。
- SAN 总开关、重生 SAN 与其余游戏规则使用 Fabric Game Rule API，并继续通过 Gemini 组件同步客户端。
- 两套旧 Forge 附魔改写为 1.21.1 数据驱动附魔，包括属性效果、伤害保护、稀有度标签和交易标签。
- 数据资源按 1.21.1 单数目录重写，共 1,354 个资源文件：422 个配方、62 个进度、65 个战利品表、177 个结构、527 个世界生成文件及标签。
- 结构 NBT 使用标签级解析写回，不采用破坏长度字段的原始字节替换。
- 自定义公共标签迁移到 `pasterdream` 命名空间，通用材料、矿石、食物、工具、盔甲使用 `c:` 约定标签；FarmersDelight、Silent's Delight 等临时联动引用全部剔除。
- 液体桶改用原版物品模型和静态生成贴图，不再依赖 Forge 流体容器模型加载器。

验收结果：局部 `compileJava` 通过；数据层 JSON 无效数、外部模组引用、`forge:` 引用和旧 ID 残留均为 0；物品模型、BlockEntity 绑定和注册目录校验均为 0 缺失。

## 第五阶段：库存与菜单（首版基础已完成，机器处理流程待迁移）

- Forge 物品处理能力迁移到 Fabric Transfer API。
- MCR 重复的物品栏 Capability 收敛为可复用的 ItemStack 组件。
- 使用原版 Container 和 Fabric Extended Screen Handler 重建菜单。
- 删除可被原版菜单同步替代的自定义槽位点击包。

验收：容器、机器、背包物品和自定义界面能正确保存、拆分和转移物品。

当前实现：

- 19 个旧菜单全部改为 Fabric `ExtendedScreenHandlerType`，打开数据只携带方块坐标、玩家槽位或虚拟菜单标记，不再为每个槽位同步写自定义网络包。
- 菜单槽位数量、坐标、玩家背包位置和输出槽/展示槽/研究槽访问规则由 `docs/menu-specs.tsv` 整理后写入统一菜单规格。
- 通用方块实体直接实现原版 `Container` 和 `WorldlyContainer`，库存持久化到方块实体 NBT，并通过 `SidedStorageBlockEntity` 向 Fabric Transfer API 暴露全部有效槽位。
- `storage_bag` 和 `storage_bag_0` 使用原版 `DataComponents.CONTAINER` 保存 9/25 格物品，不再创建 17 份 Forge Capability。
- 背包物品通过 Fabric `ItemStorage.ITEM` 注册带事务的物品存储视图，支持管道类模组插入、提取和事务回滚。
- 通用 `quickMoveStack`、玩家背包/快捷栏移动、输出槽限制和菜单有效性检查均已恢复。
- 方块右键和物品右键使用 Fabric `UseBlockCallback`、`UseItemCallback` 打开对应菜单。
- 客户端注册统一 `PasterDreamMenuScreen`，复用旧 GUI 贴图和各自尺寸；蓝图、储物袋、工作台、熔炉、箱子和研究台均能显示对应界面。

## 第六阶段：Curios、动画与客户端渲染（首版基础已完成）

- Curios 代码迁移到 `dev.arakiel.curios`。
- 饰品槽改为数据驱动的 `curiosfabric` 资源。
- 实体、方块实体、物品和盔甲迁移到 GeckoLib 4.9.3。
- Player Animator 改为复数资源目录和当前 `IPlayable` API。
- 迁移 HUD、按键、粒子、Screen 和全部客户端渲染器。

验收：专用服务器不加载客户端类，客户端模型、饰品、动作、HUD 和界面正常。

当前实现：

- 50 个旧饰品改为统一 `ICurioItem` 实现，槽位通过 `curiosfabric` 数据标签分配；头部、项链、背部、身体、戒指、腰带和护符共 7 类槽位及数量由 JSON 定义。
- 饰品属性、唯一装备限制、夜视、跳跃、融梦能量恢复和末影人/猪灵行为使用 CuriosFabric API 与 1.21.1 属性系统。
- 38 个实体通过原生或别名 Geo 模型注册 GeckoLib 渲染，另有 31 个 Geo 方块实体、2 个 Geo 物品和 3 套翅膀盔甲；没有模型的 13 个实体使用安全空渲染器。
- Player Animator 2.x 使用 `player_animations` 资源和 `AnimationStack` 层播放闪避动画。
- 34 个自定义粒子统一注册 Fabric `ParticleFactoryRegistry`。
- 19 个菜单使用原版 GUI 贴图注册客户端 Screen。
- SAN/融梦能量 HUD、瞬身键和斗篷激活键通过 Fabric HUD、KeyBinding 和 Gemini 类型化网络接入。

验收结果：公共代码不依赖 `net.minecraft.client`，`compileJava` 同时通过公共端与客户端源码编译。

## 第七阶段：世界数据、联动清理与验收（首版构建与服务器门禁已通过）

- 迁移维度、世界生成、结构、蓝图加载、标签、配方、战利品表和数据生成。
- 删除失效的 MCR 注册、Forge 垫片、联动代码和重复实现。
- 只重新引入仍然需要的联动。
- 执行编译、客户端、专用服务器、存档重载和玩法回归测试。

验收：不再存在 Forge 引用，无强制联动，单人及专用服务器均可正常游玩。

当前实现：

- 维度、维度类型、世界生成、结构模板池、结构 NBT、战利品表、进度、配方、附魔和蓝图均已迁移到 1.21.1 数据目录。
- 177 个结构 NBT 的命名空间 ID 已按标签级 NBT 解析重写；422 个配方、65 个战利品表和 527 个世界生成文件完成 ID 映射。
- 86 个旧 Forge `add_features` 与 12 个 `add_spawns` 注入改写为 Fabric `BiomeModifications`。
- 6 个旧 MCR 树装饰器类型迁移为无副作用 1.21.1 `TreeDecoratorType`，保证世界生成注册链完整。
- 蓝图目录通过 Fabric 资源重载监听器读取，蓝图菜单可读取并展示第一页物品布局。
- FarmersDelight、Silent's Delight、JEI 等当前不需要的联动引用全部移除。
- 不参与编译的 Forge/MCR 暂存源码已删除；原始 `old/` 资料仍保留。
- 项目元数据已从 Fabric 示例信息替换为 PasterDream 信息，并保留 GeckoLib、Player Animator JIJ、Gemini 与 CuriosFabric 前置关系。

验收结果：局部 `compileJava` 通过；全资源 JSON 可解析；未发现 Forge、旧 Curios 包名、外部联动、旧 ID、Java/Gradle 注释残留。

## 首版语义差距审计

首版已经通过完整构建和专用服务器启动，但 Forge 原作的 524 个 MCR procedure 不能仅靠注册表迁移视为玩法已经等价。当前仓库保留以下后续回归项：

- 101 个旧物品含交互逻辑，当前已恢复食物、Curios、装备、菜单、常见法杖/法术投射物等通用路径，仍需逐项核对卡片、地图、工具、钥匙和机器专用物品。
- 63 个旧方块含 tick 行为，当前通用方块实体已恢复按菜单容量保存、分流访问和 Fabric Transfer API，但各类机器的输入判定、加工时长、副产物和世界状态机仍需逐项迁移。
- 18 个旧药水效果覆写 tick 行为，当前已恢复资源、闪避、易伤、治疗倍率和若干常驻效果，仍需完成剩余效果的周期行为。
- 51 个旧实体的生命、伤害、护甲、击退等属性已从 Forge 源恢复，通用地面 AI 和投射物已恢复；Boss、飞行、驯服、召唤和多阶段技能仍属于后续行为回归。

机器可运行的数据/注册/资源门禁与玩法完全等价是两个验收层级。当前状态适合作为第一版移植地基和联调包，不应宣称所有 MCR procedure 已逐行等价。
