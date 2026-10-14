package net.minecraft.world.attribute.modifier;

import com.mojang.serialization.Codec;
import java.util.Map;
import java.util.Set;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.LerpFunction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.Builder;
import net.minecraft.world.level.biome.MobSpawnSettings.MobSpawnCost;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;

public interface MobSpawnSettingsModifier extends AttributeModifier<MobSpawnSettings, MobSpawnSettings> {
   static MobSpawnSettingsModifier overlay() {
      return MobSpawnSettingsModifier.Overlay.INSTANCE;
   }

   @Override
   default Codec<MobSpawnSettings> argumentCodec(final EnvironmentAttribute<MobSpawnSettings> attribute) {
      return attribute.valueCodec();
   }

   @Override
   default LerpFunction<MobSpawnSettings> argumentKeyframeLerp(final EnvironmentAttribute<MobSpawnSettings> attribute) {
      return attribute.type().keyframeLerp();
   }

   record Overlay() implements MobSpawnSettingsModifier {
      private static final MobSpawnSettingsModifier.Overlay INSTANCE = new MobSpawnSettingsModifier.Overlay();
      private static final MobCategory[] ALL_CATEGORIES = MobCategory.values();

      private static final java.util.IdentityHashMap<MobSpawnSettings, java.util.IdentityHashMap<MobSpawnSettings, MobSpawnSettings>> MEMO = new java.util.IdentityHashMap<>();
      private static int memoN;

      public MobSpawnSettings apply(final MobSpawnSettings first, final MobSpawnSettings second) {
         var m = MEMO.get(first);
         if (m != null) {
            var r = m.get(second);
            if (r != null) return r;
         } else {
            if (memoN > 4096) { MEMO.clear(); memoN = 0; }
            MEMO.put(first, m = new java.util.IdentityHashMap<>());
         }
         var r = apply0(first, second);
         m.put(second, r);
         memoN++;
         return r;
      }

      private MobSpawnSettings apply0(final MobSpawnSettings first, final MobSpawnSettings second) {
         Set<MobCategory> firstCategories = first.definedCategories();
         Set<MobCategory> secondCategories = second.definedCategories();
         Map<EntityType<?>, MobSpawnCost> firstSpawnCosts = first.allSpawnCosts();
         Map<EntityType<?>, MobSpawnCost> secondSpawnCosts = second.allSpawnCosts();
         if (firstCategories.isEmpty() && firstSpawnCosts.isEmpty()) {
            return second;
         }

         if (secondCategories.isEmpty() && secondSpawnCosts.isEmpty()) {
            return first;
         }

         if (secondCategories.containsAll(firstCategories) && secondSpawnCosts.keySet().containsAll(firstSpawnCosts.keySet())) {
            return second;
         }

         Builder builder = new Builder();

         for (MobCategory category : ALL_CATEGORIES) {
            WeightedList<SpawnerData> secondSpawns = second.getMobsInCategory(category);
            if (secondSpawns != null) {
               builder.addAllSpawns(category, secondSpawns);
            } else {
               WeightedList<SpawnerData> firstSpawns = first.getMobsInCategory(category);
               if (firstSpawns != null) {
                  builder.addAllSpawns(category, firstSpawns);
               }
            }
         }

         return builder.addAllCosts(firstSpawnCosts).addAllCosts(secondSpawnCosts).build();
      }
   }
}
