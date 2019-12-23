package de.lellson.progressivecore.inv.gui;

import de.lellson.progressivecore.inv.container.ContainerSmelter;
import de.lellson.progressivecore.inv.tile.TileEntitySmelter;
import de.lellson.progressivecore.misc.Constants;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSmelter extends GuiContainer {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(Constants.MOD_ID, "textures/gui/container/smelter.png");

    private final InventoryPlayer playerInventory;
    private final IInventory inv;

    public GuiSmelter(InventoryPlayer playerInv, IInventory inv) {
        super(new ContainerSmelter(playerInv, inv));
        this.playerInventory = playerInv;
        this.inv = inv;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = this.inv.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        if (TileEntitySmelter.isBurning(this.inv))
        {
            int k = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(i + 25, j + 54 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = this.getCookProgressScaled(15);
        if (l > 0)
        	this.drawTexturedModalRect(i + 109, j + 38, 176, 14, l, 10);
    }

    private int getCookProgressScaled(int pixels) {
        int i = this.inv.getField(2);
        int j = this.inv.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    private int getBurnLeftScaled(int pixels) {
        int i = this.inv.getField(1);

        if (i == 0)
        {
            i = 200;
        }

        return this.inv.getField(0) * pixels / i;
    }
}
