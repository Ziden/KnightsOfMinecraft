package net.minecraft.server.v1_12_R1;

import java.util.Random;

public class BiomeDecorator
{
  protected boolean a;
  protected BlockPosition b;
  protected CustomWorldSettingsFinal c;
  protected WorldGenerator d = new WorldGenClay(4);
  protected WorldGenerator e = new WorldGenSand(Blocks.SAND, 7);
  protected WorldGenerator f = new WorldGenSand(Blocks.GRAVEL, 6);
  protected WorldGenerator g;
  protected WorldGenerator h;
  protected WorldGenerator i;
  protected WorldGenerator j;
  protected WorldGenerator k;
  protected WorldGenerator l;
  protected WorldGenerator m;
  protected WorldGenerator n;
  protected WorldGenerator o;
  protected WorldGenerator p;
  protected WorldGenerator q;
  protected WorldGenFlowers r = new WorldGenFlowers(Blocks.YELLOW_FLOWER, BlockFlowers.EnumFlowerVarient.DANDELION);
  protected WorldGenerator s = new WorldGenMushrooms(Blocks.BROWN_MUSHROOM);
  protected WorldGenerator t = new WorldGenMushrooms(Blocks.RED_MUSHROOM);
  protected WorldGenerator u = new WorldGenHugeMushroom();
  protected WorldGenerator v = new WorldGenReed();
  protected WorldGenerator w = new WorldGenCactus();
  protected WorldGenerator x = new WorldGenWaterLily();
  protected int y;
  protected int z;
  protected float A = 0.1F;
  protected int B = 2;
  protected int C = 1;
  protected int D;
  protected int E;
  protected int F;
  protected int G;
  protected int H = 1;
  protected int I = 3;
  protected int J = 1;
  protected int K;
  public boolean L = true;
  
  public void a(World paramWorld, Random paramRandom, BiomeBase paramBiomeBase, BlockPosition paramBlockPosition)
  {
    if (this.a) {
      throw new RuntimeException("Already decorating");
    }
    this.c = CustomWorldSettingsFinal.CustomWorldSettings.a(paramWorld.getWorldData().getGeneratorOptions()).b();
    this.b = paramBlockPosition;
    
    this.g = new WorldGenMinable(Blocks.DIRT.getBlockData(), this.c.J);
    this.h = new WorldGenMinable(Blocks.GRAVEL.getBlockData(), this.c.N);
    this.i = new WorldGenMinable(Blocks.STONE.getBlockData().set(BlockStone.VARIANT, BlockStone.EnumStoneVariant.GRANITE), this.c.R);
    this.j = new WorldGenMinable(Blocks.STONE.getBlockData().set(BlockStone.VARIANT, BlockStone.EnumStoneVariant.DIORITE), this.c.V);
    this.k = new WorldGenMinable(Blocks.STONE.getBlockData().set(BlockStone.VARIANT, BlockStone.EnumStoneVariant.ANDESITE), this.c.Z);
    this.l = new WorldGenMinable(Blocks.STONE.getBlockData(), this.c.ad);
    this.m = new WorldGenMinable(Blocks.STONE.getBlockData(), this.c.ah);
    this.n = new WorldGenMinable(Blocks.STONE.getBlockData(), this.c.al);
    this.o = new WorldGenMinable(Blocks.STONE.getBlockData(), this.c.ap);
    this.p = new WorldGenMinable(Blocks.STONE.getBlockData(), this.c.at);
    this.q = new WorldGenMinable(Blocks.STONE.getBlockData(), this.c.ax);
    
    a(paramBiomeBase, paramWorld, paramRandom);
    
    this.a = false;
  }
  
  protected void a(BiomeBase paramBiomeBase, World paramWorld, Random paramRandom)
  {
    a(paramWorld, paramRandom);
    int i3;
    int i1;
    int i2;
    for ( i1 = 0; i1 < this.I; i1++)
    {
      i2 = paramRandom.nextInt(16) + 8;
      i3 = paramRandom.nextInt(16) + 8;
      this.e.generate(paramWorld, paramRandom, paramWorld.q(this.b.a(i2, 0, i3)));
    }
    for (i1 = 0; i1 < this.J; i1++)
    {
      i2 = paramRandom.nextInt(16) + 8;
      i3 = paramRandom.nextInt(16) + 8;
      this.d.generate(paramWorld, paramRandom, paramWorld.q(this.b.a(i2, 0, i3)));
    }
    for (i1 = 0; i1 < this.H; i1++)
    {
      i2 = paramRandom.nextInt(16) + 8;
      i3 = paramRandom.nextInt(16) + 8;
      this.f.generate(paramWorld, paramRandom, paramWorld.q(this.b.a(i2, 0, i3)));
    }
    i1 = this.z;
    if (paramRandom.nextFloat() < this.A) {
      i1++;
    }
    int i4;
    for ( i2 = 0; i2 < i1; i2++)
    {
      i3 = paramRandom.nextInt(16) + 8;
      i4 = paramRandom.nextInt(16) + 8;
      
      WorldGenTreeAbstract localWorldGenTreeAbstract = paramBiomeBase.a(paramRandom);
      localWorldGenTreeAbstract.e();
      
      BlockPosition localBlockPosition2 = paramWorld.getHighestBlockYAt(this.b.a(i3, 0, i4));
      if (localWorldGenTreeAbstract.generate(paramWorld, paramRandom, localBlockPosition2)) {
        localWorldGenTreeAbstract.a(paramWorld, paramRandom, localBlockPosition2);
      }
    }
    for (i2 = 0; i2 < this.K; i2++)
    {
      i3 = paramRandom.nextInt(16) + 8;
      i4 = paramRandom.nextInt(16) + 8;
      this.u.generate(paramWorld, paramRandom, paramWorld.getHighestBlockYAt(this.b.a(i3, 0, i4)));
    }
    int i5;
    int i7;
    Object localObject1;
    Object localObject2;
    for (i2 = 0; i2 < this.B; i2++)
    {
      i3 = paramRandom.nextInt(16) + 8;
      i4 = paramRandom.nextInt(16) + 8;
      i5 = paramWorld.getHighestBlockYAt(this.b.a(i3, 0, i4)).getY() + 32;
      if (i5 > 0)
      {
        i7 = paramRandom.nextInt(i5);
        
        localObject1 = this.b.a(i3, i7, i4);
        localObject2 = paramBiomeBase.a(paramRandom, (BlockPosition)localObject1);
        BlockFlowers localBlockFlowers = ((BlockFlowers.EnumFlowerVarient)localObject2).a().a();
        if (localBlockFlowers.getBlockData().getMaterial() != Material.AIR)
        {
          this.r.a(localBlockFlowers, (BlockFlowers.EnumFlowerVarient)localObject2);
          this.r.generate(paramWorld, paramRandom, (BlockPosition)localObject1);
        }
      }
    }
    for (i2 = 0; i2 < this.C; i2++)
    {
      i3 = paramRandom.nextInt(16) + 8;
      i4 = paramRandom.nextInt(16) + 8;
      i5 = paramWorld.getHighestBlockYAt(this.b.a(i3, 0, i4)).getY() * 2;
      if (i5 > 0)
      {
        i7 = paramRandom.nextInt(i5);
        
        paramBiomeBase.b(paramRandom).generate(paramWorld, paramRandom, this.b.a(i3, i7, i4));
      }
    }
    for (i2 = 0; i2 < this.D; i2++)
    {
      i3 = paramRandom.nextInt(16) + 8;
      i4 = paramRandom.nextInt(16) + 8;
      i5 = paramWorld.getHighestBlockYAt(this.b.a(i3, 0, i4)).getY() * 2;
      if (i5 > 0)
      {
        i7 = paramRandom.nextInt(i5);
        
        new WorldGenDeadBush().generate(paramWorld, paramRandom, this.b.a(i3, i7, i4));
      }
    }
    for (i2 = 0; i2 < this.y; i2++)
    {
      i3 = paramRandom.nextInt(16) + 8;
      i4 = paramRandom.nextInt(16) + 8;
      i5 = paramWorld.getHighestBlockYAt(this.b.a(i3, 0, i4)).getY() * 2;
      if (i5 > 0)
      {
        i7 = paramRandom.nextInt(i5);
        
        localObject1 = this.b.a(i3, i7, i4);
        while (((BlockPosition)localObject1).getY() > 0)
        {
          localObject2 = ((BlockPosition)localObject1).down();
          if (!paramWorld.isEmpty((BlockPosition)localObject2)) {
            break;
          }
          localObject1 = localObject2;
        }
        this.x.generate(paramWorld, paramRandom, (BlockPosition)localObject1);
      }
    }
    int i6;
    for (i2 = 0; i2 < this.E; i2++)
    {
      if (paramRandom.nextInt(4) == 0)
      {
        i3 = paramRandom.nextInt(16) + 8;
        i4 = paramRandom.nextInt(16) + 8;
        BlockPosition localBlockPosition1 = paramWorld.getHighestBlockYAt(this.b.a(i3, 0, i4));
        this.s.generate(paramWorld, paramRandom, localBlockPosition1);
      }
      if (paramRandom.nextInt(8) == 0)
      {
        i3 = paramRandom.nextInt(16) + 8;
        i4 = paramRandom.nextInt(16) + 8;
        i6 = paramWorld.getHighestBlockYAt(this.b.a(i3, 0, i4)).getY() * 2;
        if (i6 > 0)
        {
          i7 = paramRandom.nextInt(i6);
          localObject1 = this.b.a(i3, i7, i4);
          this.t.generate(paramWorld, paramRandom, (BlockPosition)localObject1);
        }
      }
    }
    if (paramRandom.nextInt(4) == 0)
    {
      i2 = paramRandom.nextInt(16) + 8;
      i3 = paramRandom.nextInt(16) + 8;
      i4 = paramWorld.getHighestBlockYAt(this.b.a(i2, 0, i3)).getY() * 2;
      if (i4 > 0)
      {
        i6 = paramRandom.nextInt(i4);
        this.s.generate(paramWorld, paramRandom, this.b.a(i2, i6, i3));
      }
    }
    if (paramRandom.nextInt(8) == 0)
    {
      i2 = paramRandom.nextInt(16) + 8;
      i3 = paramRandom.nextInt(16) + 8;
      i4 = paramWorld.getHighestBlockYAt(this.b.a(i2, 0, i3)).getY() * 2;
      if (i4 > 0)
      {
        i6 = paramRandom.nextInt(i4);
        this.t.generate(paramWorld, paramRandom, this.b.a(i2, i6, i3));
      }
    }
    for (i2 = 0; i2 < this.F; i2++)
    {
      i3 = paramRandom.nextInt(16) + 8;
      i4 = paramRandom.nextInt(16) + 8;
      i6 = paramWorld.getHighestBlockYAt(this.b.a(i3, 0, i4)).getY() * 2;
      if (i6 > 0)
      {
        i7 = paramRandom.nextInt(i6);
        this.v.generate(paramWorld, paramRandom, this.b.a(i3, i7, i4));
      }
    }
    for (i2 = 0; i2 < 10; i2++)
    {
      i3 = paramRandom.nextInt(16) + 8;
      i4 = paramRandom.nextInt(16) + 8;
      i6 = paramWorld.getHighestBlockYAt(this.b.a(i3, 0, i4)).getY() * 2;
      if (i6 > 0)
      {
        i7 = paramRandom.nextInt(i6);
        this.v.generate(paramWorld, paramRandom, this.b.a(i3, i7, i4));
      }
    }
    if (paramRandom.nextInt(32) == 0)
    {
      i2 = paramRandom.nextInt(16) + 8;
      i3 = paramRandom.nextInt(16) + 8;
      i4 = paramWorld.getHighestBlockYAt(this.b.a(i2, 0, i3)).getY() * 2;
      if (i4 > 0)
      {
        i6 = paramRandom.nextInt(i4);
        new WorldGenPumpkin().generate(paramWorld, paramRandom, this.b.a(i2, i6, i3));
      }
    }
    for (i2 = 0; i2 < this.G; i2++)
    {
      i3 = paramRandom.nextInt(16) + 8;
      i4 = paramRandom.nextInt(16) + 8;
      i6 = paramWorld.getHighestBlockYAt(this.b.a(i3, 0, i4)).getY() * 2;
      if (i6 > 0)
      {
        i7 = paramRandom.nextInt(i6);
        this.w.generate(paramWorld, paramRandom, this.b.a(i3, i7, i4));
      }
    }
    if (this.L)
    {
      for (i2 = 0; i2 < 50; i2++)
      {
        i3 = paramRandom.nextInt(16) + 8;
        i4 = paramRandom.nextInt(16) + 8;
        i6 = paramRandom.nextInt(248) + 8;
        if (i6 > 0)
        {
          i7 = paramRandom.nextInt(i6);
          localObject1 = this.b.a(i3, i7, i4);
          new WorldGenLiquids(Blocks.FLOWING_WATER).generate(paramWorld, paramRandom, (BlockPosition)localObject1);
        }
      }
      for (i2 = 0; i2 < 20; i2++)
      {
        i3 = paramRandom.nextInt(16) + 8;
        i4 = paramRandom.nextInt(16) + 8;
        i6 = paramRandom.nextInt(paramRandom.nextInt(paramRandom.nextInt(240) + 8) + 8);
        BlockPosition localBlockPosition3 = this.b.a(i3, i6, i4);
        
        new WorldGenLiquids(Blocks.FLOWING_LAVA).generate(paramWorld, paramRandom, localBlockPosition3);
      }
    }
  }
  
  protected void a(World paramWorld, Random paramRandom)
  {
    a(paramWorld, paramRandom, this.c.K, this.g, this.c.L, this.c.M);
    a(paramWorld, paramRandom, this.c.O, this.h, this.c.P, this.c.Q);
    a(paramWorld, paramRandom, this.c.W, this.j, this.c.X, this.c.Y);
    a(paramWorld, paramRandom, this.c.S, this.i, this.c.T, this.c.U);
    a(paramWorld, paramRandom, this.c.aa, this.k, this.c.ab, this.c.ac);
    a(paramWorld, paramRandom, this.c.ae, this.l, this.c.af, this.c.ag);
    a(paramWorld, paramRandom, this.c.ai, this.m, this.c.aj, this.c.ak);
    a(paramWorld, paramRandom, this.c.am, this.n, this.c.an, this.c.ao);
    a(paramWorld, paramRandom, this.c.aq, this.o, this.c.ar, this.c.as);
    a(paramWorld, paramRandom, this.c.au, this.p, this.c.av, this.c.aw);
    b(paramWorld, paramRandom, this.c.ay, this.q, this.c.az, this.c.aA);
  }
  
  protected void a(World paramWorld, Random paramRandom, int paramInt1, WorldGenerator paramWorldGenerator, int paramInt2, int paramInt3)
  {
      int i1;
    if (paramInt3 < paramInt2)
    {
      i1 = paramInt2;
      paramInt2 = paramInt3;
      paramInt3 = i1;
    }
    else if (paramInt3 == paramInt2)
    {
      if (paramInt2 < 255) {
        paramInt3++;
      } else {
        paramInt2--;
      }
    }
    for ( i1 = 0; i1 < paramInt1; i1++)
    {
      BlockPosition localBlockPosition = this.b.a(paramRandom.nextInt(16), paramRandom.nextInt(paramInt3 - paramInt2) + paramInt2, paramRandom.nextInt(16));
      paramWorldGenerator.generate(paramWorld, paramRandom, localBlockPosition);
    }
  }
  
  protected void b(World paramWorld, Random paramRandom, int paramInt1, WorldGenerator paramWorldGenerator, int paramInt2, int paramInt3)
  {
    for (int i1 = 0; i1 < paramInt1; i1++)
    {
      BlockPosition localBlockPosition = this.b.a(paramRandom.nextInt(16), paramRandom.nextInt(paramInt3) + paramRandom.nextInt(paramInt3) + paramInt2 - paramInt3, paramRandom.nextInt(16));
      paramWorldGenerator.generate(paramWorld, paramRandom, localBlockPosition);
    }
  }
}
