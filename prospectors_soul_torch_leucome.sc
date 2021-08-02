// scarpet 1.4

// stay loaded
__config() -> (
   m(
      l('stay_loaded','true')
   )
);


global_mineral_ores = l(
	l('coal_ore','dust 0.1 0.1 0.1 0.5'),
	l('redstone_ore', 'dust 0.9 0.1 0.1 0.5'),
	l('lapis_ore', 'dust 0.1 0.1 1.0 0.5')
);
global_metal_ores = l(
	l('iron_ore', 'dust 0.6 0.3 0.1 0.5'),
	l('gold_ore','dust 0.9 0.9 0.0 0.5'),
	l('nether_gold_ore','dust 0.9 0.9 0.0 0.5'),
	l('ancient_debris', 'dust 0.2 0.2 0.2 0.5'),
	l('copper_ore', 'dust 0.7 0.5 0.0 0.5')
);

global_crystal_ores = l(
	l('nether_quartz_ore','electric_spark 1 1 1 0.5'),
	l('diamond_ore','dust 0.3 0.8 1.0 0.5'),
	l('emerald_ore', 'dust 0.4 1.0 0.4 0.5')
);


__on_tick() ->
(
	for (player('!spectating'), player = _;
		item_mainhand = player ~ 'holds';
	if(!(query(player, 'holds', 'offhand'):0 == 'soul_torch'), return());
			player_pos = pos(player);
			l(x, y, z) = map(player_pos, floor(_));
			player_in_caves = top('terrain',player_pos) > (y+3);
			// modify reference Y level, around diamond level for surface tracking
			base_y = if(player_in_caves, y, 8);
			loop(360,
				try (
					l(block_x, block_y, block_z) = l(x, base_y, z) 
							+ l(rand(10)-rand(10), rand(10)-rand(10), rand(10)-rand(10));
					block = block(block_x, block_y, block_z);
					if (block ~ '_debris'||'_ore',
						for(range(0, 7),
							l(oreblock, ore_particle) = get(global_crystal_ores, _);
							if (block == oreblock,
								if( player_in_caves,
									particle_line(ore_particle, 
										player_pos+l(0,1.2,0), 
										block_x+0.5, block_y+0.5, block_z+0.5, 
										0.8
									)
								,//else	
									particle(ore_particle, 
										block_x, top('terrain',block)+1 , block_z,
										20
									)
								);
								throw()
							)
						)
					)
				)
			)
		)
);
 
