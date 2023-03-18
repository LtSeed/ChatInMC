package ltseed.chatinmc.PlayerInteraction.GUI.FinalGUI;

import ltseed.Exception.InvalidChatterException;
import ltseed.chatinmc.ChatInMC;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.GUI.EnabledView;
import ltseed.chatinmc.PlayerInteraction.GUI.PaginatedGUI;
import ltseed.chatinmc.PlayerInteraction.GUI.SimpleGUI;
import ltseed.chatinmc.PlayerInteraction.PlayerConversation;
import ltseed.chatinmc.Talker.ChatGPT.ChatGPTBuilder;
import ltseed.chatinmc.Talker.Chatter;
import ltseed.chatinmc.Talker.MessageBuilder;
import ltseed.chatinmc.Utils.TimeConverter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

import static ltseed.chatinmc.Talker.Chatter.getCore;
import static ltseed.chatinmc.Utils.EntityMaterialMapper.getMaterial;

@EnabledView
public class ChatterCreateGUI extends SimpleGUI {

    public static final Map<Player, ChatterCreateGUI> creating = new HashMap<>();

    String name = "未设置";
    String description = "未设置";

    String choose_entity = "未选择";
    Material material = null;
    String core = null;
    double talkDistance = 15;

    long dur = 60*1000*60;

    String core_name = "未指定";

    String projectId = "未设置";

    String suffix= "默认值";
    int max_tokens = 512;
    double temperature = 1;
    int top_p = 1;
    int n = 1;
    Integer logprobs = null;
    double presence_penalty = 0;
    double frequency_penalty = 0;
    int best_of = 1;

    public static ChatterCreateGUI getInstance(Player player){
        ChatterCreateGUI orDefault = creating.getOrDefault(player, new ChatterCreateGUI(player));
        orDefault.update();
        return orDefault;
    }

    private ChatterCreateGUI(Player player) {
        super("新建一个Chatter！", new HashMap<>());
        update();
        open(player);
        creating.put(player,this);
    }

    public void update() {
        List<String> nameLore = new ArrayList<>();
        nameLore.add("Click to edit");
        Button nameButton = new Button((short) 3, (short) 3, Material.NAME_TAG,"名称：" +  name, nameLore) {
            /**
             */
            @Override
            public void call(Player player) {
                new PlayerConversation(player).startConversation("请输入Chatter的名字！", response -> {
                    ChatterCreateGUI chatterCreateGUI = creating.get(player);
                    chatterCreateGUI.name = response;
                    creating.put(player,chatterCreateGUI);
                    open(player);
                });
            }
        };
        addButton(nameButton);

        List<String> descLore = new ArrayList<>();
        descLore.add("Click to edit");
        Button descButton = new Button((short) 3, (short) 2, Material.PAPER,"描述：" +  description, descLore) {
            /**
             */
            @Override
            public void call(Player player) {
                new PlayerConversation(player).startConversation("请输入Chatter的描述！", response -> {
                    ChatterCreateGUI chatterCreateGUI = creating.get(player);
                    chatterCreateGUI.description = response;
                    creating.put(player,chatterCreateGUI);
                    open(player);
                });
            }
        };
        addButton(descButton);


        List<String> entityChooseLore = new ArrayList<>();
        entityChooseLore.add("Click to edit");
        if(material == null) material = Material.COW_SPAWN_EGG;
        Button entityChooseButton = new Button((short) 4, (short) 2, material,"选择实体id：" +  choose_entity, entityChooseLore) {
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
                    Location location = entity.getLocation();
                    lore.add("位置: " + location.getBlockX()+","+location.getBlockY()+","+location.getBlockZ());
                    lore.add("实体ID: " + entity.getEntityId());

                    Button button = new Button(-1, getMaterial(entity.getType()),entity.getName(),lore) {
                        final UUID uuid = entity.getUniqueId();
                        final Material m = getMaterial(entity.getType());
                        @Override
                        public void call(Player p) {
                            ChatterCreateGUI chatterCreateGUI = creating.get(p);
                            chatterCreateGUI.choose_entity = uuid.toString();
                            chatterCreateGUI.material = m;
                            creating.put(p,chatterCreateGUI);
                            open(p);
                        }
                    };
                    buttons.add(button);
                }
                ArrayList<Button> otherButtons = new ArrayList<>();
                otherButtons.add(new Button((short) 2,(short) 6,Material.ARROW,"返回",null) {
                    @Override
                    public void call(Player player) {
                        open(player);
                    }
                });
                // 创建一个分页GUI
                PaginatedGUI gui = new PaginatedGUI("实体列表", buttons, otherButtons);

                // 打开指定页码的页面
                gui.open(player, 1);
            }
        };
        addButton(entityChooseButton);


        List<String> coreChooseLore = new ArrayList<>();
        coreChooseLore.add("Click to edit");

        Button coreChooseButton = new Button((short) 6, (short) 4, Material.PAPER,"核心名：" + core_name, coreChooseLore) {
            /**
             */
            @Override
            public void call(Player player) {
                List<Button> buttons = new ArrayList<>();
                for (String s : ChatInMC.models) {
                    // 将模型转化为按钮

                    Button button = new Button(-1, Material.WRITABLE_BOOK,s,null) {
                        @Override
                        public void call(Player p) {
                            ChatterCreateGUI chatterCreateGUI = creating.get(player);
                            chatterCreateGUI.core_name = s;
                            chatterCreateGUI.core = s;
                            creating.put(player,chatterCreateGUI);
                            open(p);
                        }
                    };
                    buttons.add(button);
                }
                ArrayList<Button> otherButtons = new ArrayList<>();
                otherButtons.add(new Button(46,Material.ARROW,"返回",null) {
                    @Override
                    public void call(Player player) {
                        open(player);
                    }
                });
                // 创建一个分页GUI
                PaginatedGUI gui = new PaginatedGUI("核心列表", buttons,otherButtons);

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
                        ChatterCreateGUI chatterCreateGUI = creating.get(player);
                        chatterCreateGUI.talkDistance = Double.parseDouble(response);
                        creating.put(player,chatterCreateGUI);
                    } catch (NumberFormatException e) {
                        player.sendMessage("你的数字格式有误！");
                    }
                    open(player);
                });
            }
        };
        addButton(distanceSetButton);

        if(core_name.equalsIgnoreCase("DialogFlow")){
            List<String> proIdSetLore = new ArrayList<>();
            proIdSetLore.add("Click to edit");
            Button proIdSetButton = new Button((short) 5, (short) 4, Material.BOOK,"projectId：" +  projectId, proIdSetLore) {
                /**
                 */
                @Override
                public void call(Player player) {
                    new PlayerConversation(player).startConversation("请输入DialogFlow ProjectID:", response -> {

                        ChatterCreateGUI chatterCreateGUI = creating.get(player);
                        chatterCreateGUI.projectId = response;
                        creating.put(player,chatterCreateGUI);
                        open(player);
                    });
                }
            };
            addButton(proIdSetButton);
        } else if (!core_name.equals("未指定核心")){
            List<String> lore = new ArrayList<>();
            lore.add("Click to edit");
            lore.add("Reading docs of ChatGPT before editing!");
            lore.add("String, default to null");
            Button suffixSetButton = new Button((short) 1, (short) 6, Material.PAPER,"suffix：" + suffix, lore) {
                @Override
                public void call(Player player) {
                    new PlayerConversation(player).startConversation("请输入suffix！", response -> {
                        ChatterCreateGUI chatterCreateGUI = creating.get(player);
                        chatterCreateGUI.suffix = response;
                        creating.put(player,chatterCreateGUI);
                        open(player);
                    });
                }
            };
            addButton(suffixSetButton);
            lore.remove(2);

            lore.add("int, default to 512");
            Button max_tokens_setButton = new Button((short) 2, (short) 6, Material.PAPER,"max_tokens：" + max_tokens, lore) {
                @Override
                public void call(Player player) {
                    new PlayerConversation(player).startConversation("请输入max_tokens！", response -> {
                        try {
                            ChatterCreateGUI chatterCreateGUI = creating.get(player);
                            chatterCreateGUI.max_tokens = Integer.parseInt(response);
                            creating.put(player,chatterCreateGUI);
                        } catch (NumberFormatException e) {
                            player.sendMessage("你的数字格式有误！");
                        }
                        open(player);
                    });
                }
            };
            addButton(max_tokens_setButton);
            lore.remove(2);

            lore.add("double, default to 1, range in 0.0-2.0");
            Button temperature_setButton = new Button((short) 3, (short) 6, Material.PAPER,"temperature：" + temperature, lore) {
                @Override
                public void call(Player player) {
                    new PlayerConversation(player).startConversation("请输入temperature！", response -> {
                        try {
                            ChatterCreateGUI chatterCreateGUI = creating.get(player);
                            chatterCreateGUI.temperature = Double.parseDouble(response);
                            creating.put(player,chatterCreateGUI);

                        } catch (NumberFormatException e) {
                            player.sendMessage("你的数字格式有误！");
                        }
                        open(player);
                    });
                }
            };
            addButton(temperature_setButton);
            lore.remove(2);

            lore.add("int, default to 1");
            Button top_p_setButton = new Button((short) 4, (short) 6, Material.PAPER,"top_p：" + top_p, lore) {
                @Override
                public void call(Player player) {
                    new PlayerConversation(player).startConversation("请输入top_p！", response -> {
                        try {

                            ChatterCreateGUI chatterCreateGUI = creating.get(player);
                            chatterCreateGUI.top_p = Integer.parseInt(response);
                            creating.put(player,chatterCreateGUI);
                        } catch (NumberFormatException e) {
                            player.sendMessage("你的数字格式有误！");
                        }
                        open(player);
                    });
                }
            };
            addButton(top_p_setButton);
            lore.remove(2);

            lore.add("int, default to 1");
            Button n_setButton = new Button((short) 5, (short) 6, Material.PAPER,"n：" + n, lore) {
                @Override
                public void call(Player player) {
                    new PlayerConversation(player).startConversation("请输入n！", response -> {
                        try {
                            ChatterCreateGUI chatterCreateGUI = creating.get(player);
                            chatterCreateGUI.n = Integer.parseInt(response);
                            creating.put(player,chatterCreateGUI);
                        } catch (NumberFormatException e) {
                            player.sendMessage("你的数字格式有误！");
                        }
                        open(player);
                    });
                }
            };
            addButton(n_setButton);
            lore.remove(2);

            lore.add("Integer, default to null");
            Button logprobs_setButton = new Button((short) 6, (short) 6, Material.PAPER,"logprobs：" + logprobs, lore) {
                @Override
                public void call(Player player) {
                    new PlayerConversation(player).startConversation("请输入logprobs！", response -> {
                        try {
                            ChatterCreateGUI chatterCreateGUI = creating.get(player);
                            chatterCreateGUI.logprobs = Integer.parseInt(response);
                            creating.put(player,chatterCreateGUI);

                        } catch (NumberFormatException e) {
                            player.sendMessage("你的数字格式有误！");
                        }
                        open(player);
                    });
                }
            };
            addButton(logprobs_setButton);
            lore.remove(2);

            lore.add("double, default to 0.0");
            Button presence_penalty_setButton = new Button((short) 7, (short) 6, Material.PAPER,"presence_penalty：" + presence_penalty, lore) {
                @Override
                public void call(Player player) {
                    new PlayerConversation(player).startConversation("请输入presence_penalty_setButton！", response -> {
                        try {
                            ChatterCreateGUI chatterCreateGUI = creating.get(player);
                            chatterCreateGUI.presence_penalty = Double.parseDouble(response);
                            creating.put(player,chatterCreateGUI);

                        } catch (NumberFormatException e) {
                            player.sendMessage("你的数字格式有误！");
                        }
                        open(player);
                    });
                }
            };
            addButton(presence_penalty_setButton);
            lore.remove(2);

            lore.add("double, default to 0.0");
            Button frequency_penalty_setButton = new Button((short) 8, (short) 6, Material.PAPER,"frequency_penalty：" + frequency_penalty, lore) {
                @Override
                public void call(Player player) {
                    new PlayerConversation(player).startConversation("请输入frequency_penalty！", response -> {
                        try {
                            ChatterCreateGUI chatterCreateGUI = creating.get(player);
                            chatterCreateGUI.frequency_penalty = Double.parseDouble(response);
                            creating.put(player,chatterCreateGUI);
                        } catch (NumberFormatException e) {
                            player.sendMessage("你的数字格式有误！");
                        }
                        open(player);
                    });
                }
            };
            addButton(frequency_penalty_setButton);
            lore.remove(2);

            lore.add("int, default to 1");
            Button best_of_setButton = new Button((short) 9, (short) 6, Material.PAPER,"best_of：" + best_of, lore) {
                @Override
                public void call(Player player) {
                    new PlayerConversation(player).startConversation("请输入best_of！", response -> {
                        try {
                            ChatterCreateGUI chatterCreateGUI = creating.get(player);
                            chatterCreateGUI.best_of = Integer.parseInt(response);
                            creating.put(player,chatterCreateGUI);
                        } catch (NumberFormatException e) {
                            player.sendMessage("你的数字格式有误！");
                        }
                        open(player);
                    });
                }
            };
            addButton(best_of_setButton);
            lore.remove(2);
        }

        Button dialogTimeSetButton = new Button((short) 7, (short) 4, Material.CLOCK,"对话持续时间："+ TimeConverter.getCode(dur), nameLore) {
            @Override
            public void call(Player player) {
                new PlayerConversation(player).startConversation("请输入对话持续时间，格式为*天*时*分*秒*毫秒", response -> {
                    try {
                        ChatterCreateGUI chatterCreateGUI = creating.get(player);
                        chatterCreateGUI.dur = TimeConverter.getTime(response);
                        creating.put(player,chatterCreateGUI);
                    } catch (Exception e) {
                        player.sendMessage("你的格式有误！");
                    }
                    open(player);
                });
            }
        };
        addButton(dialogTimeSetButton);
        Button createButton = new Button((short) 5, (short) 3, Material.GREEN_WOOL, "Create", null) {
            @Override
            public void call(Player player) {
                if(!Objects.equals(choose_entity, "未选择实体") && !Objects.equals(name, "Name") && !Objects.equals(description, "Description") && material != null && core != null) {
                    Chatter chatter = new Chatter();
                    UUID uuid = UUID.fromString(choose_entity);
                    chatter.setUuid(uuid);
                    chatter.getEntity();
                    chatter.setTalk_distance(talkDistance);
                    chatter.setDialogTime(dur);
                    chatter.setName(name);
                    chatter.setDescription(description);
                    try {
                        MessageBuilder core1 = getCore(dur, core_name, projectId);
                        if(core1 instanceof ChatGPTBuilder){
                            ChatGPTBuilder gptBuilder = (ChatGPTBuilder) core1;
                            gptBuilder.setBest_of(best_of);
                            gptBuilder.setFrequency_penalty(frequency_penalty);
                            gptBuilder.setLogprobs(logprobs);
                            gptBuilder.setMax_tokens(max_tokens);
                            gptBuilder.setN(n);
                            if(!suffix.equals("默认值"))
                                gptBuilder.setSuffix(suffix);
                            gptBuilder.setPresence_penalty(presence_penalty);
                            gptBuilder.setTop_p(top_p);
                            gptBuilder.setTemperature(temperature);
                            chatter.setCore(gptBuilder);
                        } else chatter.setCore(core1);
                    } catch (InvalidChatterException e) {
                        ChatInMC.debug.err(e.getLocalizedMessage());
                    }
                    ChatInMC.chatters.put(uuid,chatter);
                    player.closeInventory();
                }
            }
        };
        addButton(createButton);

        Button cancelButton = new Button((short) 5, (short) 5, Material.BARRIER, ChatColor.RED+"Cancel", null) {
            @Override
            public void call(Player player) {
                player.closeInventory();
                creating.remove(player);
            }
        };
        addButton(cancelButton);
    }

    ChatterCreateGUI(){
        super();
    }
}
