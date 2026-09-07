<script>
  import { base } from '$app/paths';
  import Footer from '$lib/components/Footer.svelte';

  // Subdomain redirect check if accessed on root domain via subdomain
  if (typeof window !== 'undefined') {
    const host = window.location.hostname.toLowerCase();
    if (host.startsWith('stackable-items.') || host.startsWith('stackable.')) {
      window.location.replace(`${base}/stackable-items/` + window.location.search + window.location.hash);
    } else if (host.startsWith('easily-critical.') || host.startsWith('easily.')) {
      window.location.replace(`${base}/easily-critical/` + window.location.search + window.location.hash);
    }
  }

  const mods = [
    {
      id: 'stackable-items',
      title: 'Stackable Items',
      category: 'Inventory & Storage',
      categoryBadge: 'text-[#58a6ff] bg-[#388bfd]/15 border-[#388bfd]/30',
      borderHover: 'hover:border-[#388bfd] hover:shadow-[0_0_40px_rgba(56,139,253,0.12)]',
      titleHover: 'group-hover:text-[#58a6ff]',
      iconContainer: 'bg-[#1c2738] border-[#388bfd]/40 shadow-[0_0_24px_rgba(56,139,253,0.25)] ring-1 ring-[#388bfd]/20',
      icon: `${base}/assets/stackable-items/icon.png`,
      desc: 'Removes vanilla item stack limits up to 999,999 per inventory slot and container with a vanilla-safe fallback system that preserves item data when uninstalled.',
      features: [
        'Custom stack limits (default 64 up to 999,999+)',
        'Vanilla-Safe Storage & Delta Consumption Tracking',
        'Native support for Bundles, Dispensers, and Furnaces'
      ],
      link: `${base}/stackable-items/`
    },
    {
      id: 'easily-critical',
      title: 'Easily Critical',
      category: 'Combat & Mechanics',
      categoryBadge: 'text-[#3fb950] bg-[#238636]/15 border-[#2ea043]/30',
      borderHover: 'hover:border-[#2ea043] hover:shadow-[0_0_40px_rgba(46,160,67,0.15)]',
      titleHover: 'group-hover:text-[#3fb950]',
      iconContainer: 'bg-[#182a1d] border-[#3fb950]/40 shadow-[0_0_24px_rgba(63,185,80,0.25)] ring-1 ring-[#3fb950]/20',
      icon: `${base}/assets/easily-critical/icon.png`,
      desc: 'Customizable Minecraft combat mechanics: standing Mace smash attacks (0 blocks), critical hits without jumping, fall damage immunity, and zero attack cooldowns.',
      features: [
        'Configurable Mace Smash triggers (0.0, 1.0, 1.5 blocks)',
        'Consistent critical hits without jumping required',
        'In-game configuration via interactive Mod Menu GUI'
      ],
      link: `${base}/easily-critical/`
    }
  ];
</script>

<svelte:head>
  <title>Minecraft Fabric Mods • Documentation</title>
  <meta name="description" content="Official documentation portal for Minecraft Java Edition Fabric mods: Stackable Items and Easily Critical." />
</svelte:head>

<div class="flex flex-col min-h-screen bg-[#090d13] text-[#e6edf3] relative selection:bg-[#238636] selection:text-white overflow-x-hidden">
  <!-- Subtle Background Glow -->
  <div class="absolute inset-0 bg-[radial-gradient(ellipse_80%_60%_at_50%_-15%,rgba(35,134,54,0.18),transparent_70%)] pointer-events-none"></div>

  <main class="flex-1 flex flex-col justify-center py-12 sm:py-20 relative z-10">
    <div class="max-w-[1120px] mx-auto px-5 w-full">
      <!-- Main Mod Cards Grid -->
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-8 max-w-5xl mx-auto w-full">
        {#each mods as mod (mod.id)}
          <div class="group relative bg-[#12161f]/90 hover:bg-[#151a24] border border-[#262c36] {mod.borderHover} rounded-2xl p-7 sm:p-8 transition-all duration-300 flex flex-col justify-between">
            <div>
              <div class="flex items-center justify-between mb-6">
                <div class="w-14 h-14 rounded-xl border p-1.5 flex items-center justify-center group-hover:scale-105 transition-all {mod.iconContainer}">
                  <img src={mod.icon} alt="{mod.title} Icon" class="w-full h-full object-contain filter brightness-115 contrast-105 drop-shadow-md" />
                </div>
                <div>
                  <span class="text-xs font-medium border px-2.5 py-1 rounded-full {mod.categoryBadge}">
                    {mod.category}
                  </span>
                </div>
              </div>

              <h2 class="text-2xl font-bold text-white mb-3 {mod.titleHover} transition-colors">
                {mod.title}
              </h2>

              <p class="text-sm text-[#8b949e] leading-relaxed mb-6">
                {mod.desc}
              </p>

              <!-- Highlights Checklist -->
              <ul class="space-y-2.5 mb-8 text-sm text-[#c9d1d9]">
                {#each mod.features as feat}
                  <li class="flex items-start gap-2.5">
                    <svg class="w-4 h-4 text-[#3fb950] mt-1 shrink-0" viewBox="0 0 20 20" fill="currentColor">
                      <path fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd" />
                    </svg>
                    <span>{feat}</span>
                  </li>
                {/each}
              </ul>
            </div>

            <!-- Buttons -->
            <div class="pt-4 border-t border-[#21262d]">
              <a
                href={mod.link}
                class="w-full inline-flex items-center justify-center gap-2 font-semibold text-sm px-4 py-2.5 rounded-lg bg-[#238636] hover:bg-[#2ea043] text-white transition-all shadow-sm group/btn"
              >
                <span>View Documentation</span>
                <svg class="w-4 h-4 group-hover/btn:translate-x-1 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2.5">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M9 5l7 7-7 7" />
                </svg>
              </a>
            </div>
          </div>
        {/each}
      </div>
    </div>
  </main>

  <Footer text="Minecraft Java Mods - Fabric Only" />
</div>
