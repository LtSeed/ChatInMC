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

/**

 A GUI for creating a new {@link Chatter}. This GUI is used to gather information

 from the player about the new Chatter that they want to create.

 It extends {@link SimpleGUI} and is annotated with {@link EnabledView}.
 @author LtSeed
 @version 1.0
 */
@EnabledView
public class ChatterCreateGUI extends SimpleGUI {

    /**

     A map of player instances that are creating a new Chatter and their associated
     {@link ChatterCreateGUI} instances.
     */
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
    ArrayList<String> basicTrain = new ArrayList<>();

    /**

     Returns a singleton instance of the {@link ChatterCreateGUI} for the specified player.
     If the player does not already have a {@link ChatterCreateGUI} instance, a new one is created.
     @param player The player to get the {@link ChatterCreateGUI} instance for
     @return The {@link ChatterCreateGUI} instance for the player
     */
    public static ChatterCreateGUI getInstance(Player player){
        ChatterCreateGUI orDefault = creating.getOrDefault(player, new ChatterCreateGUI(player));
        orDefault.update();
        return orDefault;
    }

    /**

     Constructs a new {@link ChatterCreateGUI} instance for the specified player.
     The GUI is opened immediately and added to the {@link #creating} map.
     @param player The player to create the GUI for
     */
    private ChatterCreateGUI(Player player) {
        super("新建一个Chatter！", new HashMap<>());
        update();
        open(player);
        creating.put(player,this);
    }

    /**

     Updates the buttons in the GUI with the latest information.
     */
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
             * Opens a {@link PlayerConversation} with the player to get a new name for the Chatter.
             * Updates the GUI with the new name.
             *
             * @param player The player who clicked the button
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

        List<String> basicTrainEditLore = new ArrayList<>();
        basicTrainEditLore.add("Click to edit");
        basicTrainEditLore.addAll(basicTrain);
        Button basicTrainEditButton = new Button((short) 9, (short) 1, Material.WRITTEN_BOOK,"基本训练字符串: size = " + basicTrain.size(), basicTrainEditLore) {
            /**
             */
            @Override
            public void call(Player player) {
                openBasicTrainSetGUI(player);
            }
        };
        addButton(basicTrainEditButton);

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
                            gptBuilder.setBasicTrain(basicTrain);
                            chatter.setCore(gptBuilder);
                        } else chatter.setCore(core1);
                    } catch (InvalidChatterException e) {
                        ChatInMC.debug.err(e.getLocalizedMessage());
                    }
                    ChatInMC.chatters.put(uuid,chatter);
                    player.closeInventory();
                    player.sendMessage("chatter 成功创建！");
                } else {
                    open(player);
                }
            }
        };
        if(!Objects.equals(choose_entity, "未选择实体") && !Objects.equals(name, "Name") && !Objects.equals(description, "Description") && material != null && core != null)
            addButton(createButton);

        Button cancelButton = new Button((short) 5, (short) 5, Material.BARRIER, ChatColor.RED+"Cancel", null) {
            @Override
            public void call(Player player) {
                player.closeInventory();
                creating.remove(player);
                player.sendMessage("chatter 成功删除！");
            }
        };
        addButton(cancelButton);
    }

    /**

     Opens the GUI for setting the basic training data for the AI.
     @param player The player for whom the GUI is being opened.
     */
    protected void openBasicTrainSetGUI(Player player) {
        List<Button> buttons = new ArrayList<>();
        int i = 1;
        for (String s : basicTrain) {
            // 将basicTrain转化为按钮

            Button button = new Button(-1, Material.WRITABLE_BOOK,String.valueOf(i++),splitString(s)) {
                @Override
                public void call(Player p) {
                    new PlayerConversation(player).startConversation("请输入新的字符串！", response -> {
                        try {
                            ChatterCreateGUI chatterCreateGUI = creating.get(player);
                            ArrayList<String> n = new ArrayList<>();
                            for (String s1 : chatterCreateGUI.basicTrain) {
                                if(s1.equalsIgnoreCase(s)) n.add(response);
                                else n.add(s1);
                            }
                            chatterCreateGUI.basicTrain = n;
                            creating.put(player,chatterCreateGUI);
                        } catch (Exception e) {
                            player.sendMessage("格式有误！");
                        }
                        openBasicTrainSetGUI(player);
                    });
                    player.closeInventory();
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
        otherButtons.add(new Button(47,Material.ARROW,"添加新的",null) {
            @Override
            public void call(Player player) {
                new PlayerConversation(player).startConversation("请输入新的字符串！", response -> {
                    try {
                        ChatterCreateGUI chatterCreateGUI = creating.get(player);
                        chatterCreateGUI.basicTrain.add(response);
                        creating.put(player,chatterCreateGUI);
                    } catch (Exception e) {
                        player.sendMessage("格式有误！");
                    }
                    openBasicTrainSetGUI(player);
                });
                player.closeInventory();
            }
        });
        otherButtons.add(new Button(48,Material.BARRIER,"进入删除界面",null) {
            @Override
            public void call(Player player) {
                openBasicTrainDeleteGUI(player);
            }
        });
        // 创建一个分页GUI
        PaginatedGUI gui = new PaginatedGUI("核心列表", buttons,otherButtons);

        // 打开指定页码的页面
        gui.open(player, 1);
    }

    /**

     Opens the GUI for deleting the basic training data for the AI.
     @param player The player for whom the GUI is being opened.
     */
    protected void openBasicTrainDeleteGUI(Player player) {
        List<Button> buttons = new ArrayList<>();
        int i = 1;
        for (String s : basicTrain) {
            // 将basicTrain转化为按钮
            Button button = new Button(-1, Material.BARRIER,String.valueOf(i++),splitString(s)) {
                @Override
                public void call(Player p) {
                    try {
                        ChatterCreateGUI chatterCreateGUI = creating.get(player);
                        chatterCreateGUI.basicTrain.remove(s);
                        creating.put(player,chatterCreateGUI);
                    } catch (Exception e) {
                        player.sendMessage("出现了未知问题！");
                    }
                    openBasicTrainDeleteGUI(player);
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
        otherButtons.add(new Button(48,Material.EMERALD,"退出删除界面",null) {
            @Override
            public void call(Player player) {
                openBasicTrainSetGUI(player);
            }
        });
        // 创建一个分页GUI
        PaginatedGUI gui = new PaginatedGUI("核心列表", buttons,otherButtons);

        // 打开指定页码的页面
        gui.open(player, 1);
    }

    /**

     Splits a given string into substrings of maximum length 10 and returns them in a list.
     @param input the string to be split
     @return a list of substrings of maximum length 10
     */
    public static List<String> splitString(String input) {
        List<String> result = new ArrayList<>();
        int startIndex = 0;
        int endIndex = Math.min(input.length(), 10);
        while (startIndex < input.length()) {
            String substring = input.substring(startIndex, endIndex);
            result.add(substring);
            startIndex = endIndex;
            endIndex = Math.min(startIndex + 10, input.length());
        }
        return result;
    }

    ChatterCreateGUI(){
        super();
    }
}
