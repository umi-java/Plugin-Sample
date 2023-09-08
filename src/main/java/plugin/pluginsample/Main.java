package plugin.pluginsample;

import java.io.IOException;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

  @Override
  public void onEnable() {
    saveDefaultConfig();

    Bukkit.getPluginManager().registerEvents(this, this);
    getCommand("setLevel").setExecutor(new setLevelCommand(this));
    getCommand("allSetLevel").setExecutor(new AllSetLevelCommand());
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e){
    Player player = e.getPlayer();
    World world = player.getWorld();
    Location playerlocation = player.getLocation();

    world.spawn(new Location(world, playerlocation.getX()+3, playerlocation.getY(), playerlocation.getZ()), Rabbit.class);

  }


  private int count;
  /**
   * プレイヤーがスニークを開始/終了する際に起動されるイベントハンドラ。
   *
   * @param e イベント
   */
  @EventHandler
  public void onPlayerToggleSneak(PlayerToggleSneakEvent e) throws IOException {
    // イベント発生時のプレイヤーやワールドなどの情報を変数に持つ。
    Player player = e.getPlayer();
    World world = player.getWorld();

    List<Color> colorList = List.of(Color.BLUE,Color.RED,Color.YELLOW,Color.BLACK,Color.PURPLE);
    if(count % 2 == 0){
      for(Color color : colorList){


      // 花火オブジェクトをプレイヤーのロケーション地点に対して出現させる。
      Firework firework = world.spawn(player.getLocation(), Firework.class);

      // 花火オブジェクトが持つメタ情報を取得。
      FireworkMeta fireworkMeta = firework.getFireworkMeta();

      // メタ情報に対して設定を追加したり、値の上書きを行う。
      // 今回は青色で星型の花火を打ち上げる。
      fireworkMeta.addEffect(
          FireworkEffect.builder()
              .withColor(color)
              .with(Type.STAR)
              .withFlicker()
              .build());
      fireworkMeta.setPower(1 * 3 - 1);

      // 追加した情報で再設定する。
      firework.setFireworkMeta(fireworkMeta);
      }


    }
    count++;
  }

  @EventHandler
  public void onPlayerJoinEvent(PlayerJoinEvent e){
    e.getPlayer().sendMessage(ChatColor.GREEN + "参加したよー");
  }



}