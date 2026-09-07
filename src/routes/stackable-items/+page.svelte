<script>
  import { base } from '$app/paths';
  import Navbar from '$lib/components/Navbar.svelte';
  import Footer from '$lib/components/Footer.svelte';
  import Modal from '$lib/components/Modal.svelte';
  import { stackableData } from '$lib/data/stackable.js';

  let modalOpen = $state(false);
  let modalImage = $state('');
  let copiedCmd = $state('');

  function openPreview(src) {
    modalImage = src;
    modalOpen = true;
  }

  function copy(text) {
    navigator.clipboard.writeText(text).then(() => {
      copiedCmd = text;
      setTimeout(() => {
        if (copiedCmd === text) copiedCmd = '';
      }, 2000);
    });
  }
</script>

<svelte:head>
  <title>Stackable Items - Fabric Mod Documentation</title>
  <meta name="description" content={stackableData.meta.description} />
  <link rel="icon" href="{base}/assets/stackable-items/icon.png" />
</svelte:head>

<div class="flex flex-col min-h-screen bg-[#0d1117] text-[#e6edf3]" id="top">
  <Navbar
    title={stackableData.meta.title}
    icon="{base}/assets/stackable-items/icon.png"
    links={stackableData.links}
    githubUrl={stackableData.meta.github}
  />

  <main class="flex-1">
    <!-- Hero -->
    <header class="py-16 sm:py-20 border-b border-[#21262d]">
      <div class="max-w-[1080px] mx-auto px-5">
        <div class="inline-flex items-center text-xs font-medium text-[#3fb950] bg-[#238636]/15 border border-[#2ea043]/40 px-3 py-1 rounded-full mb-4">
          {stackableData.meta.version}
        </div>
        <h1 class="text-3xl sm:text-4xl font-bold tracking-tight mb-3.5 text-[#e6edf3]">
          {stackableData.meta.title}
        </h1>
        <p class="text-lg text-[#8b949e] max-w-2xl leading-relaxed">
          {stackableData.meta.description}
        </p>
      </div>
    </header>

    <!-- Screenshots -->
    <section id="screenshots" class="py-14 border-b border-[#21262d] scroll-mt-20">
      <div class="max-w-[1080px] mx-auto px-5">
        <div class="mb-6">
          <h2 class="text-2xl font-bold tracking-tight mb-2 text-[#e6edf3]">Screenshots</h2>
        </div>

        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
          {#each [1, 2, 3] as n}
            <div
              class="relative aspect-video bg-[#090c10] border border-[#30363d] rounded-md overflow-hidden cursor-pointer shadow-md hover:-translate-y-1 hover:border-[#58a6ff] hover:shadow-xl transition-all duration-200 group"
              onclick={() => openPreview(`${base}/assets/stackable-items/screenshots/screenshot-${n}.png`)}
              role="button"
              tabindex="0"
              onkeydown={(e) => e.key === 'Enter' && openPreview(`${base}/assets/stackable-items/screenshots/screenshot-${n}.png`)}
            >
              <img
                src="{base}/assets/stackable-items/screenshots/screenshot-{n}.png"
                alt="Screenshot {n}"
                class="w-full h-full object-cover block"
              />
              <div class="absolute inset-0 bg-[#0d1117]/40 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity">
                <svg viewBox="0 0 24 24" class="w-8 h-8 text-white drop-shadow-md stroke-current stroke-2 fill-none" stroke="currentColor">
                  <path d="M15 3h6v6M9 21H3v-6M21 3l-7 7M3 21l7-7" />
                </svg>
              </div>
            </div>
          {/each}
        </div>
      </div>
    </section>

    <!-- Features -->
    <section id="features" class="py-14 border-b border-[#21262d] scroll-mt-20">
      <div class="max-w-[1080px] mx-auto px-5">
        <div class="mb-6">
          <h2 class="text-2xl font-bold tracking-tight mb-2 text-[#e6edf3]">Key Features</h2>
          <p class="text-sm sm:text-base text-[#8b949e]">Core functionality implemented across Minecraft item and container systems.</p>
        </div>

        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
          {#each stackableData.features as { title, desc }}
            <div class="bg-[#161b22] border border-[#30363d] rounded-md p-6 hover:-translate-y-0.5 hover:border-[#58a6ff] transition-all">
              <h3 class="text-base font-semibold mb-2 text-[#e6edf3]">{title}</h3>
              <p class="text-sm text-[#8b949e] leading-relaxed">{desc}</p>
            </div>
          {/each}
        </div>
      </div>
    </section>

    <!-- Comparisons (Mechanics) -->
    <section id="mechanics" class="py-14 border-b border-[#21262d] scroll-mt-20">
      <div class="max-w-[1080px] mx-auto px-5">
        <div class="mb-6">
          <h2 class="text-2xl font-bold tracking-tight mb-2 text-[#e6edf3]">Comparisons</h2>
          <p class="text-sm sm:text-base text-[#8b949e]">Comparison between without mod (vanilla) and mod.</p>
        </div>

        <div class="border border-[#30363d] rounded-md overflow-x-auto">
          <table class="w-full border-collapse text-sm">
            <thead>
              <tr class="bg-[#21262d] border-b border-[#30363d]">
                <th class="px-4 py-3 text-left font-semibold text-[#e6edf3] w-1/4">Item / Block</th>
                <th class="px-4 py-3 text-left font-semibold text-[#e6edf3]">Vanilla Behavior</th>
                <th class="px-4 py-3 text-left font-semibold text-[#e6edf3]">With Stackable Items</th>
              </tr>
            </thead>
            <tbody>
              {#each stackableData.comparisons as { name, vanilla, mod }}
                <tr class="border-b border-[#21262d] last:border-b-0 hover:bg-white/[0.02] transition-colors">
                  <td class="px-4 py-3 font-semibold text-[#e6edf3]">{name}</td>
                  <td class="px-4 py-3 text-[#8b949e]">{vanilla}</td>
                  <td class="px-4 py-3 text-[#8b949e]">{mod}</td>
                </tr>
              {/each}
            </tbody>
          </table>
        </div>

        <div class="bg-[#bb8009]/10 border-l-4 border-[#9e6a03] p-4 sm:p-5 rounded-r-md my-6 text-[#d29922] text-sm leading-relaxed">
          <div class="font-semibold text-[#f2cc60] mb-1">Vanilla Depletion Safety Rule</div>
          <p>
            If you uninstall this mod and load your world in vanilla Minecraft, your oversized stacks will appear as standard 64-stacks (or 1 for tools) while keeping your reserve safely attached.
            <strong>Always leave at least 1 item in the slot.</strong> If you consume the stack to 0 in vanilla, Minecraft destroys the slot and the hidden reserve cannot be recovered.
          </p>
        </div>
      </div>
    </section>

    <!-- Commands -->
    <section id="commands" class="py-14 border-b border-[#21262d] scroll-mt-20">
      <div class="max-w-[1080px] mx-auto px-5">
        <div class="mb-6">
          <h2 class="text-2xl font-bold tracking-tight mb-2 text-[#e6edf3]">Commands</h2>
          <p class="text-sm sm:text-base text-[#8b949e]">In-game commands for configuration (requires operator permission in multiplayer).</p>
        </div>

        <div class="border border-[#30363d] rounded-md overflow-x-auto">
          <table class="w-full border-collapse text-sm">
            <thead>
              <tr class="bg-[#21262d] border-b border-[#30363d]">
                <th class="px-4 py-3 text-left font-semibold text-[#e6edf3] w-1/3">Command</th>
                <th class="px-4 py-3 text-left font-semibold text-[#e6edf3]">Description</th>
                <th class="px-4 py-3 text-right font-semibold text-[#e6edf3] w-[100px]">Action</th>
              </tr>
            </thead>
            <tbody>
              {#each stackableData.commands as { tag, cmd, desc }}
                <tr class="border-b border-[#21262d] last:border-b-0 hover:bg-white/[0.02] transition-colors">
                  <td class="px-4 py-3">
                    <span class="font-mono text-xs text-[#3fb950] bg-[#238636]/15 px-2 py-0.5 rounded">
                      {tag}
                    </span>
                  </td>
                  <td class="px-4 py-3 text-[#8b949e]">{desc}</td>
                  <td class="px-4 py-3 text-right">
                    <button
                      class="bg-[#161b22] border border-[#30363d] text-[#8b949e] hover:text-[#e6edf3] hover:border-[#8b949e] hover:bg-[#1c2128] text-xs font-medium px-2.5 py-1 rounded cursor-pointer transition-all"
                      onclick={() => copy(cmd)}
                    >
                      {copiedCmd === cmd ? 'Copied!' : 'Copy'}
                    </button>
                  </td>
                </tr>
              {/each}
            </tbody>
          </table>
        </div>
      </div>
    </section>

    <!-- Troubleshooting -->
    <section id="troubleshooting" class="py-14 border-b border-[#21262d] scroll-mt-20">
      <div class="max-w-[1080px] mx-auto px-5">
        <div class="mb-6">
          <h2 class="text-2xl font-bold tracking-tight mb-2 text-[#e6edf3]">Troubleshooting</h2>
          <p class="text-sm sm:text-base text-[#8b949e]">Common questions, redstone mechanics, and edge case solutions.</p>
        </div>

        <div class="space-y-4">
          {#each stackableData.troubleshooting as { title, cause, solution }}
            <div class="bg-[#161b22] border border-[#30363d] rounded-md p-5">
              <div class="text-base font-semibold mb-2 text-[#f2cc60]">{title}</div>
              <div class="text-sm text-[#8b949e] space-y-1 leading-relaxed">
                <p><strong class="text-[#e6edf3]">Cause:</strong> {cause}</p>
                <p><strong class="text-[#e6edf3]">Solution:</strong> {solution}</p>
              </div>
            </div>
          {/each}
        </div>
      </div>
    </section>

    <!-- Installation -->
    <section id="installation" class="py-14 scroll-mt-20">
      <div class="max-w-[1080px] mx-auto px-5">
        <div class="mb-6">
          <h2 class="text-2xl font-bold tracking-tight mb-2 text-[#e6edf3]">Installation</h2>
          <p class="text-sm sm:text-base text-[#8b949e]">Installation guide for client and dedicated servers.</p>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-4 mb-8">
          {#each stackableData.steps as { index, title, desc }}
            <div class="bg-[#161b22] border border-[#30363d] rounded-md p-5">
              <div class="font-mono text-xs font-semibold text-[#3fb950] mb-2">{index}</div>
              <div class="text-base font-semibold text-[#e6edf3] mb-2">{title}</div>
              <div class="text-sm text-[#8b949e] leading-relaxed">{desc}</div>
            </div>
          {/each}
        </div>

        <div class="text-center">
          <a
            href="#installation"
            class="inline-flex items-center font-semibold text-sm px-6 py-3 rounded-md bg-[#238636] hover:bg-[#2ea043] text-white border border-white/10 transition-colors shadow-sm"
          >
            Download Stackable Items (.jar)
          </a>
        </div>
      </div>
    </section>
  </main>

  <Footer text="Stackable Items • Fabric Loader Only" />
  <Modal bind:open={modalOpen} imageSrc={modalImage} />
</div>
