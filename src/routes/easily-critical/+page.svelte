<script>
  import { base } from '$app/paths';
  import Navbar from '$lib/components/Navbar.svelte';
  import Footer from '$lib/components/Footer.svelte';
  import Modal from '$lib/components/Modal.svelte';
  import { easilyCriticalData } from '$lib/data/easily-critical.js';

  let previewSrc = $state('');
  let previewOpen = $state(false);

  function openPreview(src) {
    previewSrc = src;
    previewOpen = true;
  }
</script>

<svelte:head>
  <title>Easily Critical - Fabric Mod Documentation</title>
  <meta name="description" content={easilyCriticalData.meta.description} />
  <link rel="icon" href="{base}/assets/easily-critical/icon.png" />
</svelte:head>

<div class="flex flex-col min-h-screen bg-[#0d1117] text-[#e6edf3]" id="top">
  <Navbar
    title={easilyCriticalData.meta.title}
    icon="{base}/assets/easily-critical/icon.png"
    links={easilyCriticalData.links}
    githubUrl={easilyCriticalData.meta.github}
  />

  <main class="flex-1">
    <!-- Hero -->
    <header class="py-16 sm:py-20 border-b border-[#21262d]">
      <div class="max-w-[1080px] mx-auto px-5">
        <div class="inline-flex items-center text-xs font-medium text-[#3fb950] bg-[#238636]/15 border border-[#2ea043]/40 px-3 py-1 rounded-full mb-4">
          {easilyCriticalData.meta.version}
        </div>
        <h1 class="text-3xl sm:text-4xl font-bold tracking-tight mb-3.5 text-[#e6edf3]">
          {easilyCriticalData.meta.title}
        </h1>
        <p class="text-lg text-[#8b949e] max-w-2xl leading-relaxed">
          {easilyCriticalData.meta.description}
        </p>
      </div>
    </header>

    <!-- Features -->
    <section id="features" class="py-14 border-b border-[#21262d] scroll-mt-20">
      <div class="max-w-[1080px] mx-auto px-5">
        <div class="mb-6">
          <h2 class="text-2xl font-bold tracking-tight mb-2 text-[#e6edf3]">Key Features</h2>
          <p class="text-sm sm:text-base text-[#8b949e]">Combat mechanics and weapon enhancement.</p>
        </div>

        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
          {#each easilyCriticalData.features as { title, desc }}
            <div class="bg-[#161b22] border border-[#30363d] rounded-md p-6 hover:-translate-y-0.5 hover:border-[#58a6ff] transition-all">
              <h3 class="text-base font-semibold mb-2 text-[#e6edf3]">{title}</h3>
              <p class="text-sm text-[#8b949e] leading-relaxed">{desc}</p>
            </div>
          {/each}
        </div>
      </div>
    </section>

    <!-- Screenshots -->
    <section id="screenshots" class="py-14 border-b border-[#21262d] scroll-mt-20">
      <div class="max-w-[1080px] mx-auto px-5">
        <div class="mb-6">
          <h2 class="text-2xl font-bold tracking-tight mb-2 text-[#e6edf3]">Screenshots</h2>
          <p class="text-sm sm:text-base text-[#8b949e]">Visual preview of Mace smash attacks, critical combat, and Wind Burst flight in action.</p>
        </div>

        <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
          {#each [1, 2, 3] as n}
            <div
              class="group relative rounded-lg overflow-hidden border border-[#30363d] bg-[#161b22] aspect-video cursor-pointer hover:border-[#2ea043] transition-all duration-200"
              onclick={() => openPreview(`${base}/assets/easily-critical/screenshots/screenshot-${n}.png`)}
              role="button"
              tabindex="0"
              onkeydown={(e) => e.key === 'Enter' && openPreview(`${base}/assets/easily-critical/screenshots/screenshot-${n}.png`)}
            >
              <img
                src="{base}/assets/easily-critical/screenshots/screenshot-{n}.png"
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

    <!-- Configuration Table -->
    <section id="config" class="py-14 border-b border-[#21262d] scroll-mt-20">
      <div class="max-w-[1080px] mx-auto px-5">
        <div class="mb-6">
          <h2 class="text-2xl font-bold tracking-tight mb-2 text-[#e6edf3]">Configuration File</h2>
          <p class="text-sm sm:text-base text-[#8b949e]">
            Direct configuration via <code class="font-mono text-xs text-[#3fb950]">config/easily_critical.json</code>.
          </p>
        </div>

        <div class="border border-[#30363d] rounded-md overflow-x-auto">
          <table class="w-full border-collapse text-sm">
            <thead>
              <tr class="bg-[#21262d] border-b border-[#30363d]">
                <th class="px-4 py-3 text-left font-semibold text-[#e6edf3] w-1/4">Property</th>
                <th class="px-4 py-3 text-left font-semibold text-[#e6edf3] w-1/3">Accepted Values</th>
                <th class="px-4 py-3 text-left font-semibold text-[#e6edf3] w-1/6">Default</th>
                <th class="px-4 py-3 text-left font-semibold text-[#e6edf3]">Description</th>
              </tr>
            </thead>
            <tbody>
              {#each easilyCriticalData.configParams as { name, values, def, desc }}
                <tr class="border-b border-[#21262d] last:border-b-0 hover:bg-white/[0.02] transition-colors">
                  <td class="px-4 py-3 font-mono text-xs text-[#3fb950]">{name}</td>
                  <td class="px-4 py-3 font-mono text-xs text-[#3fb950] bg-[#238636]/10 px-2 py-0.5 rounded">{values}</td>
                  <td class="px-4 py-3 font-mono text-xs text-[#8b949e]">{def}</td>
                  <td class="px-4 py-3 text-[#8b949e]">{desc}</td>
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
          <p class="text-sm sm:text-base text-[#8b949e]">Common questions and configuration solutions.</p>
        </div>

        <div class="space-y-4">
          {#each easilyCriticalData.troubleshooting as { title, cause, solution }}
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
          {#each easilyCriticalData.steps as { index, title, desc }}
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
            Download Easily Critical (.jar)
          </a>
        </div>
      </div>
    </section>
  </main>

  <Modal bind:open={previewOpen} imageSrc={previewSrc} />

  <Footer text="Easily Critical • Fabric Loader Only" />
</div>
