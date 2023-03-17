package ltseed.chatinmc.PlayerInteraction.GUI.FinalGUI;

import ltseed.Exception.InvalidChatterException;
import ltseed.chatinmc.ChatInMC;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.GUI.EnabledView;
import ltseed.chatinmc.PlayerInteraction.GUI.PaginatedGUI;
import ltseed.chatinmc.PlayerInteraction.GUI.SimpleGUI;
import ltseed.chatinmc.PlayerInteraction.PlayerConversation;
import ltseed.chatinmc.Talker.Chatter;
import ltseed.chatinmc.Utils.TimeConverter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

import static ltseed.chatinmc.Talker.Chatter.getCore;
import static ltseed.chatinmc.Utils.EntityMaterialMapper.getMaterial;

@EnabledView
public class ChatterCreateGUI extends SimpleGUI {

    @Override
    public void enableView() {
        super.enableView();
    }

    private static final Map<Player, ChatterCreateGUI> creating = new HashMap<>();

    String name = "Name";
    String description = "Description";

    String choose_entity = "未选择实体";
    Material material = null;
    String core = null;
    double talkDistance = 15;

    long dur = 60*1000*60;

    String core_name = "未指定核心";

    String projectId = "未设置projectId";

    public static ChatterCreateGUI getInstance(Player player){
        return creating.getOrDefault(player, new ChatterCreateGUI(player));
    }

    private ChatterCreateGUI(Player player) {
        super("新建一个Chatter！", new HashMap<>());
        init(player);
    }

    private void init(Player player) {
        List<String> nameLore = new ArrayList<>();
        nameLore.add("Click to edit");
        Button nameButton = new Button((short) 2, (short) 3, Material.NAME_TAG, name, nameLore) {
            /**
             */
            @Override
            public void call(Player player) {
                new PlayerConversation(player).startConversation("请输入Chatter的名字！", response -> {
                    name = response;
                    open(player);
                });
            }
        };
        addButton(nameButton);

        List<String> descLore = new ArrayList<>();
        descLore.add("Click to edit");
        Button descButton = new Button((short) 4, (short) 3, Material.PAPER, description, descLore) {
            /**
             */
            @Override
            public void call(Player player) {
                new PlayerConversation(player).startConversation("请输入Chatter的描述！", response -> {
                    description = response;
                    open(player);
                });
            }
        };
        addButton(descButton);


        List<String> entityChooseLore = new ArrayList<>();
        entityChooseLore.add("Click to edit");
        if(material == null) material = Material.COW_SPAWN_EGG;
        Button entityChooseButton = new Button((short) 4, (short) 2, material, choose_entity, entityChooseLore) {
            /**
             */
            @Override
            public void call(Player player) {
                List<Button> buttons = new ArrayList<>();
                List<Entity> entities = player.getNearbyEntities(10, 10, 10);
                for (Entity entity : entities) {
                    // 将实体转化为按钮
                    List<String> lore = new ArrayList<>();
                    lore.add(entity.getName());
                    lore.add("位置: " + entity.getLocation());
                    lore.add("实体ID: " + entity.getEntityId());

                    Button button = new Button(0, getMaterial(entity.getType()),entity.getCustomName(),lore) {
                        final UUID uuid = entity.getUniqueId();
                        final Material m = getMaterial(entity.getType());
                        @Override
                        public void call(Player p) {
                            choose_entity = uuid.toString();
                            material = m;
                            open(p);
                        }
                    };
                    buttons.add(button);
                }

                // 创建一个分页GUI
                PaginatedGUI gui = new PaginatedGUI("实体列表", buttons);

                // 打开指定页码的页面
                gui.open(player, 1);
            }
        };
        addButton(entityChooseButton);


        List<String> coreChooseLore = new ArrayList<>();
        coreChooseLore.add("Click to edit");

        Button coreChooseButton = new Button((short) 6, (short) 4, Material.PAPER, core_name, coreChooseLore) {
            /**
             */
            @Override
            public void call(Player player) {
                List<Button> buttons = new ArrayList<>();
                for (String s : ChatInMC.models) {
                    // 将模型转化为按钮

                    Button button = new Button(0, Material.WRITABLE_BOOK,s,null) {
                        @Override
                        public void call(Player p) {
                            core = s;
                            open(p);
                        }
                    };
                    buttons.add(button);
                }

                // 创建一个分页GUI
                PaginatedGUI gui = new PaginatedGUI("核心列表", buttons);

                // 打开指定页码的页面
                gui.open(player, 1);
            }
        };
        addButton(coreChooseButton);

        Button distanceSetButton = new Button((short) 7, (short) 3, Material.ARROW,"触发距离："+ talkDistance, nameLore) {
            @Override
            public void call(Player player) {
                new PlayerConversation(player).startConversation("请输入能够触发聊天的距离！", response -> {
                    try {
                        talkDistance = Double.parseDouble(response);
                    } catch (NumberFormatException e) {
                        player.sendMessage("你的数字格式有误！");
                    }
                    open(player);
                });
            }
        };
        addButton(distanceSetButton);

        if(core_name.equals("DialogFlow")){
            List<String> proIdSetLore = new ArrayList<>();
            proIdSetLore.add("Click to edit");
            Button proIdSetButton = new Button((short) 5, (short) 4, Material.BOOK, projectId, proIdSetLore) {
                /**
                 */
                @Override
                public void call(Player player) {
                    new PlayerConversation(player).startConversation("请输入DialogFlow ProjectID:", response -> {
                        projectId = response;
                        open(player);
                    });
                }
            };
            addButton(proIdSetButton);
        }


        Button dialogTimeSetButton = new Button((short) 7, (short) 4, Material.CLOCK,"对话持续时间："+ TimeConverter.getCode(dur), nameLore) {
            @Override
            public void call(Player player) {
                new PlayerConversation(player).startConversation("请输入对话持续时间，格式为*天*时*分*秒*毫秒", response -> {
                    try {
                        dur = TimeConverter.getTime(response);
                    } catch (Exception e) {
                        player.sendMessage("你的格式有误！");
                    }
                    open(player);
                });
            }
        };
        addButton(dialogTimeSetButton);
        Button createButton = new Button((short) 5, (short) 5, Material.GREEN_WOOL, "Create", null) {
            @Override
            public void call(Player player) {
                if(!Objects.equals(choose_entity, "未选择实体") && !Objects.equals(name, "Name") && !Objects.equals(description, "Description") && material != null && core != null) {
                    if(core_name.equals("DialogFlow")&&projectId.equals("未设置projectId")) return;
                    Chatter chatter = new Chatter();
                    UUID uuid = UUID.fromString(choose_entity);
                    chatter.setUuid(uuid);
                    chatter.getEntity();
                    chatter.setTalk_distance(talkDistance);
                    chatter.setDialogTime(dur);
                    try {
                        chatter.setCore(getCore(dur,core_name,projectId));
                    } catch (InvalidChatterException e) {
                        e.printStackTrace();
                    }
                    ChatInMC.chatters.put(uuid,chatter);
                }
            }
        };
        addButton(createButton);

        Button cancelButton = new Button((short) 4, (short) 5, Material.BARRIER, ChatColor.RED+"Cancel", null) {
            @Override
            public void call(Player player) {
                player.closeInventory();
                creating.remove(player);
            }
        };
        addButton(cancelButton);

        open(player);
        creating.put(player,this);
    }

}
