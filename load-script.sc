// scarpet 1.4

// stay loaded
__config() -> (
   m(
      l('stay_loaded','true')
   )
);

__on_player_connects(player) -> (run('reload');return('Reload own game'))
