package com.example.examplemod.machines.BlastFurnace;

import com.example.examplemod.main.EcompItems;
import com.example.examplemod.main.ModBlocks;
import com.example.examplemod.main.ExampleMod;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class BlastRecipes {
    private static final BlastRecipes INSTANCE = new BlastRecipes();
    private final List<BlastRecipe> recipes = new ArrayList<>();

    public static BlastRecipes instance() {
        return INSTANCE;
    }

    private BlastRecipes() {
        addRecipe(new ItemStack(Items.IRON_INGOT), new ItemStack(Items.COAL), new ItemStack(EcompItems.STEEL_INGOT), 10, 200);
        addRecipe(new ItemStack(ModBlocks.URANIUM_ORE), ItemStack.EMPTY, new ItemStack(EcompItems.URANIUM_INGOT), 5, 300);
    }

    public void addRecipe(ItemStack input1, ItemStack input2, ItemStack output, int energy, int time) {
        recipes.add(new BlastRecipe(input1, input2, output, energy, time));
    }

    public BlastRecipe getRecipe(ItemStack input1, ItemStack input2) {
        for (BlastRecipe recipe : recipes) {
            if (compare(input1, recipe.input1) && compare(input2, recipe.input2)) {
                return recipe;
            }
        }
        return null;
    }

    private boolean compare(ItemStack stack1, ItemStack stack2) {
        if (stack1.isEmpty() && stack2.isEmpty()) return true;
        if (stack1.isEmpty() || stack2.isEmpty()) return false;
        return stack1.getItem() == stack2.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    public static class BlastRecipe {
        public final ItemStack input1, input2, output;
        public final int energyPerTick;
        public final int cookTime;

        public BlastRecipe(ItemStack i1, ItemStack i2, ItemStack out, int energy, int time) {
            this.input1 = i1;
            this.input2 = i2;
            this.output = out;
            this.energyPerTick = energy;
            this.cookTime = time;
        }
    }
}